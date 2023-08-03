package com.hellcorp.i_movie.domain.api
import com.hellcorp.i_movie.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}