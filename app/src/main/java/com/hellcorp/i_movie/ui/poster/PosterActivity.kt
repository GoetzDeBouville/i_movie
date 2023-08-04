package com.hellcorp.i_movie.ui.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.hellcorp.i_movie.utility.Creator
import com.hellcorp.i_movie.R

class PosterActivity : AppCompatActivity() {
    private lateinit var poster: ImageView
    private val posterController = Creator.providePosterController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}