package com.pratyakshkhurana.quizapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideBottomUIMenu()
        buttonStart.setOnClickListener {
            if (enterNameEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name !", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizCategories::class.java)
                //start intent by passing intent
                //passing data to next activity
                //passed key value pair for recognition
                intent.putExtra("user", enterNameEditText.text.toString())
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
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

