package com.hellcorp.i_movie.presentation.poster

class PosterPresenter(private val view: PosterView, private val url: String) {
    fun onCreate() {
        view.setupPosterImg(url)
    }
}