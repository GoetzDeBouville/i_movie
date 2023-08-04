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
            view.showLoading()

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                view.showError(context.getString(R.string.something_went_wrong))
                                view.showToast(errorMessage)
                            }

                            movies.isEmpty() -> view.showEmpty(context.getString(R.string.nothing_found))

                            else -> view.showContent(movies)
                        }
                    }
                }
            })
        }
    }
    companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}