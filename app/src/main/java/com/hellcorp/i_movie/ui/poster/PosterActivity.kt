package com.hellcorp.i_movie.ui.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hellcorp.i_movie.utility.Creator
import com.hellcorp.i_movie.R
import com.hellcorp.i_movie.presentation.poster.PosterPresenter
import com.hellcorp.i_movie.presentation.poster.PosterView

class PosterActivity : AppCompatActivity(), PosterView {
    private lateinit var poster: ImageView
    private lateinit var posterPresenter: PosterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)
        val imageUrl = intent.extras?.getString("poster", "") ?: ""

        posterPresenter = Creator.providePosterPresenter(this, imageUrl)
        posterPresenter.onCreate()
    }

    override fun setupPosterImg(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}