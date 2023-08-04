package com.hellcorp.i_movie.presentation.movies

import com.hellcorp.i_movie.domain.models.Movie

interface MoviesView {

    fun showLoading()

    fun showError(errorMessage: String)

    fun showEmpty(emptyMessage: String)

    fun showContent(movies: List<Movie>)

    fun showToast(message: String)
}