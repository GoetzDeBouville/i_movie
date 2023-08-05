package com.hellcorp.i_movie

import android.app.Application
import com.hellcorp.i_movie.presentation.movies.MoviesSearchPresenter

class MoviesApplication : Application() {
    var moviesSearchPresenter : MoviesSearchPresenter? = null
}