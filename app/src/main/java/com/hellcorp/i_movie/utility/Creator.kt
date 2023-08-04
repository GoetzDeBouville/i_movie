package com.hellcorp.i_movie.utility

import android.app.Activity
import android.content.Context
import com.hellcorp.i_movie.data.network.RetrofitNetworkClient
import com.hellcorp.i_movie.domain.api.MoviesInteractor
import com.hellcorp.i_movie.domain.api.MoviesRepository
import com.hellcorp.i_movie.domain.impl.MoviesInteractorImpl
import com.hellcorp.i_movie.data.MoviesRepositoryImpl
import com.hellcorp.i_movie.presentation.MoviesSearchController
import com.hellcorp.i_movie.presentation.PosterController
import com.hellcorp.i_movie.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }
    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity) : PosterController {
        return  PosterController(activity)
    }
}