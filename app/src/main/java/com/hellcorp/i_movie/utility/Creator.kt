package com.hellcorp.i_movie.utility

import android.content.Context
import com.hellcorp.i_movie.data.network.RetrofitNetworkClient
import com.hellcorp.i_movie.domain.api.MoviesInteractor
import com.hellcorp.i_movie.domain.api.MoviesRepository
import com.hellcorp.i_movie.domain.impl.MoviesInteractorImpl
import com.hellcorp.i_movie.data.MoviesRepositoryImpl
import com.hellcorp.i_movie.presentation.movies.MoviesSearchPresenter
import com.hellcorp.i_movie.presentation.poster.PosterPresenter
import com.hellcorp.i_movie.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        context: Context
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(context = context)
    }

    fun providePosterPresenter(posterView: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(view = posterView, url = imageUrl)
    }
}