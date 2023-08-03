package com.hellcorp.i_movie
import com.hellcorp.i_movie.data.network.RetrofitNetworkClient
import com.hellcorp.i_movie.domain.api.MoviesInteractor
import com.hellcorp.i_movie.domain.api.MoviesRepository
import com.hellcorp.i_movie.domain.impl.MoviesInteractorImpl
import com.hellcorp.i_movie.domain.impl.MoviesRepositoryImpl
object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}