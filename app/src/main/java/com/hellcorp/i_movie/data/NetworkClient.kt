package com.hellcorp.i_movie.data
import com.hellcorp.i_movie.data.dto.Response

interface NetworkClient {
    fun doRequest(dto : Any) : Response
}