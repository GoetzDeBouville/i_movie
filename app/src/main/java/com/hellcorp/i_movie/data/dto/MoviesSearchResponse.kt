package com.hellcorp.i_movie.data.dto

data class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
) : Response()