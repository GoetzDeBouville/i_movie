package com.hellcorp.i_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class PosterActivity : AppCompatActivity() {
    private lateinit var poster: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        poster = findViewById(R.id.poster)
        val url = intent.extras?.getString("poster", "")

        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}