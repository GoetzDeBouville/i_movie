package com.hellcorp.i_movie.ui.movies.models

import com.hellcorp.i_movie.domain.models.Movie

data class MoviesState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val errorMessage: String?
)