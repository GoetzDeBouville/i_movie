package com.hellcorp.i_movie.data

import com.hellcorp.i_movie.data.dto.MoviesSearchRequest
import com.hellcorp.i_movie.data.dto.MoviesSearchResponse
import com.hellcorp.i_movie.domain.api.MoviesRepository
import com.hellcorp.i_movie.domain.models.Movie
import com.hellcorp.i_movie.utility.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение")

            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                })
            }

            else -> Resource.Error("Ошибка сервера")

        }
    }
}