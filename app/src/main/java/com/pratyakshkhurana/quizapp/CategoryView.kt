package com.pratyakshkhurana.quizapp

import android.graphics.drawable.Drawable

//second screen of recycler view
data class CategoryView(
    val image: Int,
    val category: String,
    val cardImage: Drawable,
    val icon: Drawable
)
