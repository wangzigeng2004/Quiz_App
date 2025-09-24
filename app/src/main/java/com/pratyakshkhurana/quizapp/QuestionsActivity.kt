package com.pratyakshkhurana.quizapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_questions2.*
import androidx.core.graphics.toColorInt


class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mSelectOptionPosition: Int = 0
    private var mCurrentQuestionIndex: Int = 1
    private lateinit var mQuestionList: ArrayList<Questions>
    private lateinit var mUsername: String
    private lateinit var category: String
    private var mCorrectAnswers: Int = 0


    private lateinit var mQuestion: TextView
    private lateinit var mProgressbar: ProgressBar
    private lateinit var mRating: TextView
    private lateinit var mOption1: TextView
    private lateinit var mOption2: TextView
    private lateinit var mOption3: TextView
    private lateinit var mOption4: TextView
    private lateinit var mSubmitButton: TextView

    //music effects
    private lateinit var right: MediaPlayer
    private lateinit var wrong: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions2)
        hideBottomUIMenu()
        mQuestion = tvQuestion
        mProgressbar = progressBar
        mRating = rating
        mOption1 = option1
        mOption2 = option2
        mOption3 = option3
        mOption4 = option4
        mSubmitButton = submitButton

        mUsername = intent.getStringExtra("user").toString()
        category = intent.getStringExtra("category").toString()
        mQuestionList = GetAllQuestions().fetchData()

        setQuestion()

        mOption1.setOnClickListener(this)
        mOption2.setOnClickListener(this)
        mOption3.setOnClickListener(this)
        mOption4.setOnClickListener(this)
        mSubmitButton.setOnClickListener(this)

        //music effects on wrong and right answers
        right = MediaPlayer.create(this, R.raw.right)
        wrong = MediaPlayer.create(this, R.raw.w)

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        resetToDefaultOptions()

        val currentQuestion = mQuestionList[mCurrentQuestionIndex - 1]

        if (mCurrentQuestionIndex <= mQuestionList.size) {
            mSubmitButton.text = "SUBMIT"
        }

        setProgressAnimate(mProgressbar,mCurrentQuestionIndex)
//        mProgressbar.progress = mCurrentQuestionIndex
        mRating.text = "${mCurrentQuestionIndex}/${mQuestionList.size}"
        mQuestion.text = currentQuestion.question
        mOption1.text = currentQuestion.option1
        mOption2.text = currentQuestion.option2
        mOption3.text = currentQuestion.option3
        mOption4.text = currentQuestion.option4
    }

    private fun resetToDefaultOptions() {
        val allOptions = arrayListOf<TextView>()
        allOptions.add(mOption1)
        allOptions.add(mOption2)
        allOptions.add(mOption3)
        allOptions.add(mOption4)
        mOption1.background = ContextCompat.getDrawable(this, R.drawable.bg1)
        mOption2.background = ContextCompat.getDrawable(this, R.drawable.bg2)
        mOption3.background = ContextCompat.getDrawable(this, R.drawable.bg3)
        mOption4.background = ContextCompat.getDrawable(this, R.drawable.bg4)

        for (option in allOptions) {
            option.setTextColor("#FFFFFF".toColorInt())
            option.typeface = Typeface.DEFAULT
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionPosition: Int) {
        resetToDefaultOptions()
        mSelectOptionPosition = selectedOptionPosition

        tv.setTextColor("#363A43".toColorInt())
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_clicked_bg
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {

        if (view == mOption1) {
            selectedOptionView(mOption1, 1)
        } else if (view == mOption2) {
            selectedOptionView(mOption2, 2)
        } else if (view == mOption3) {
            selectedOptionView(mOption3, 3)
        } else if (view == mOption4) {
            selectedOptionView(mOption4, 4)
        } else if (view == mSubmitButton) {

            if (mSelectOptionPosition == 0) {
                mCurrentQuestionIndex++

                if (mCurrentQuestionIndex <= mQuestionList.size) {
                    setQuestion()
                    option1.isEnabled=true
                    option2.isEnabled=true
                    option3.isEnabled=true
                    option4.isEnabled=true
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("correct", mCorrectAnswers)
                    intent.putExtra("user", mUsername)
                    intent.putExtra("total", 10)
                    startActivity(intent)
                    finish()
                }

            } else {
                val quest = mQuestionList[mCurrentQuestionIndex - 1]
                var wrongAns = 0

                if (quest.correct != mSelectOptionPosition) {
                    wrong.start()
                    wrongAns = mSelectOptionPosition
                    option1.isEnabled=false
                    option2.isEnabled=false
                    option3.isEnabled=false
                    option4.isEnabled=false
                } else {
                    right.start()
                    mCorrectAnswers++
                    option1.isEnabled=false
                    option2.isEnabled=false
                    option3.isEnabled=false
                    option4.isEnabled=false
                }

                for (i in 1..4){
                    when (i) {
                        wrongAns -> {
                            selectedOptionView(i, R.drawable.wrong_option_clicked_bg)
                        }
                        quest.correct -> {
                            selectedOptionView(i, R.drawable.correct_option_clicked_bg)
                        }
                        else -> {
                            selectedOptionView(i,R.drawable.other_options_not_clicked)
                        }
                    }
                }

                if (mCurrentQuestionIndex == mQuestionList.size) {
                    mSubmitButton.text = "FINISH"
                } else {
                    mSubmitButton.text = "NEXT"
                }

                mSelectOptionPosition = 0
            }


        }
    }

    private fun selectedOptionView(answer: Int, drawableView: Int) {

        when (answer) {
            1 -> {
                mOption1.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                mOption2.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                mOption3.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                mOption4.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
    private fun setProgressAnimate(pb: ProgressBar, progressTo: Int) {
        val animation = ObjectAnimator.ofInt(pb, "progress", pb.progress, progressTo * 10)
        animation.duration = 500
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }
}


