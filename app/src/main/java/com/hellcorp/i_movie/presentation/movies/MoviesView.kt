package com.hellcorp.i_movie.presentation.movies

import com.hellcorp.i_movie.ui.movies.models.MoviesState

interface MoviesView {
    fun render(state: MoviesState)

    fun showToast(message: String)
}