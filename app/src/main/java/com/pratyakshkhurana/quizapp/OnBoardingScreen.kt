package com.pratyakshkhurana.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import androidx.core.content.edit

class OnBoardingScreen : AppCompatActivity() {
    var OnBoardingAdapter:AdapterViewPager?=null
    var tabLayout:TabLayout?=null
    var onBoardingPager:ViewPager?=null
    var next:TextView?=null
    var position =0
    var sharedPreferences:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_on_boarding_screen)
        tabLayout=findViewById(R.id.tabLayout)
        next=findViewById(R.id.nextMove)

        //Adding Data
        val OnBoardIngData= arrayListOf<OnBoardIngItems>()
        OnBoardIngData.add(OnBoardIngItems(R.drawable.topic_display,"Different Subjects for the Quiz"))
        OnBoardIngData.add(OnBoardIngItems(R.drawable.questions_display,"Simple User Interface for the Quiz"))
        setOnBoardingViewPagerAdapter(OnBoardIngData)
        position=onBoardingPager!!.currentItem
        next?.setOnClickListener {
            if(position<OnBoardIngData.size)
            {
                position++
                onBoardingPager!!.currentItem=position
            }
            if(position==OnBoardIngData.size)
            {
                savePrefData()
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        }

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position=tab!!.position
                if(tab.position==OnBoardIngData.size-1)
                    next?.text="Get Started"
                else
                    next?.text="Next"
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
            }
        })

    }

    fun setOnBoardingViewPagerAdapter(onBoardIngItems: ArrayList<OnBoardIngItems>)
    {
        onBoardingPager=findViewById(R.id.screenPager)
        OnBoardingAdapter=AdapterViewPager(this,onBoardIngItems)
        onBoardingPager?.adapter=OnBoardingAdapter
        tabLayout?.setupWithViewPager(onBoardingPager)

    }
    private fun savePrefData()
    {
        sharedPreferences=applicationContext.getSharedPreferences("pref", MODE_PRIVATE)
        sharedPreferences!!.edit {
            putBoolean("isFirstTimeRun", true)
        }
    }
    private fun restorePrefData(): Boolean {
        sharedPreferences=applicationContext.getSharedPreferences("pref", MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun",false)
    }

}