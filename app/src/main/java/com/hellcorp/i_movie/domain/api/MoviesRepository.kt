package com.hellcorp.i_movie.domain.api

import com.hellcorp.i_movie.domain.models.Movie
import com.hellcorp.i_movie.utility.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}