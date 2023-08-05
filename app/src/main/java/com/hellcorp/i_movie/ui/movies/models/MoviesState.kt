package com.hellcorp.i_movie.ui.movies.models

import com.hellcorp.i_movie.domain.models.Movie

sealed interface MoviesState {
    object Loading : MoviesState

    data class Content(
        val movies: List<Movie>
    ) : MoviesState

    data class Error(
        val errorMessage: String
    ) : MoviesState

    data class Empty(
        val emptyMessage: String
    ) : MoviesState
}