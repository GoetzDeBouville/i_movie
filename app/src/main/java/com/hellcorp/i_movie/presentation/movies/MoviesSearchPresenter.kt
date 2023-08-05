package com.hellcorp.i_movie.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.hellcorp.i_movie.utility.Creator
import com.hellcorp.i_movie.R
import com.hellcorp.i_movie.domain.api.MoviesInteractor
import com.hellcorp.i_movie.domain.models.Movie
import com.hellcorp.i_movie.ui.movies.models.MoviesState
import moxy.MvpPresenter

class MoviesSearchPresenter(
    private val context: Context
) : MvpPresenter<MoviesView>() {
    private var latestSearchText: String? = null

    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val movies = ArrayList<Movie>()
    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(DEBOUNCE_DELAY)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        this.latestSearchText = changedText

        lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                renderState(
                                    MoviesState.Error(
                                        errorMessage = context.getString(R.string.something_went_wrong)
                                    )
                                )
                                viewState.showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState.Empty(
                                        emptyMessage = context.getString(R.string.nothing_found)
                                    )
                                )
                            }

                            else -> {
                                renderState(MoviesState.Content(movies = movies))
                            }
                        }
                    }
                }
            })
        }
    }

    private fun renderState(state: MoviesState) {
        viewState.render(state)
    }

    companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}