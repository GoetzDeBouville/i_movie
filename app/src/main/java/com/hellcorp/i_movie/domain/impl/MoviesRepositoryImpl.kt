package com.hellcorp.i_movie.domain.impl

import com.hellcorp.i_movie.data.NetworkClient
import com.hellcorp.i_movie.data.dto.MoviesSearchRequest
import com.hellcorp.i_movie.data.dto.MoviesSearchResponse
import com.hellcorp.i_movie.domain.api.MoviesRepository
import com.hellcorp.i_movie.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {
    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description)
            }
        } else {
            emptyList()
        }
    }
}