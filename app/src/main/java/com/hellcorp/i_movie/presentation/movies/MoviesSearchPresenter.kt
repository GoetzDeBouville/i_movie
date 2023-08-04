package com.hellcorp.i_movie.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.hellcorp.i_movie.utility.Creator
import com.hellcorp.i_movie.R
import com.hellcorp.i_movie.domain.api.MoviesInteractor
import com.hellcorp.i_movie.domain.models.Movie

class MoviesSearchPresenter(
    private val view: MoviesView,
    private val context: Context
) {
    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val movies = ArrayList<Movie>()
    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(changedText: String) {
        lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, DEBOUNCE_DELAY)
    }


    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            view.showPlaceholderMessage(false)
            view.showMoviesList(false)
            view.showProgressBar(true)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        view.showProgressBar(false)
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                            view.updateMoviesList(foundMovies)

                            view.showMoviesList(true)
                        }
                        if (errorMessage != null) {
                            showMessage(
                                context.getString(R.string.something_went_wrong),
                                errorMessage
                            )
                        } else if (movies.isEmpty()) {
                            showMessage(context.getString(R.string.nothing_found), "")
                        } else {
                            hideMessage()
                        }
                    }
                }
            })
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            view.showPlaceholderMessage(true)
            movies.clear()
            view.updateMoviesList(movies)

            view.changePlaceholderText(text)
            if (additionalMessage.isNotEmpty()) {
                view.showToast(additionalMessage)
            }
        } else {
            view.showPlaceholderMessage(false)
        }
    }

    private fun hideMessage() {
        view.showPlaceholderMessage(false)
    }

    companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}