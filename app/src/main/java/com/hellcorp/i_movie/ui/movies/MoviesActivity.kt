package com.hellcorp.i_movie.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hellcorp.i_movie.utility.Creator
import com.hellcorp.i_movie.R
import com.hellcorp.i_movie.domain.models.Movie
import com.hellcorp.i_movie.presentation.movies.MoviesView
import com.hellcorp.i_movie.ui.movies.models.MoviesState
import com.hellcorp.i_movie.ui.poster.PosterActivity

class MoviesActivity : AppCompatActivity(), MoviesView {

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val adapter = MoviesAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private val moviesSearchPresenter = Creator.provideMoviesSearchPresenter(this, this)
    private var textWatcher: TextWatcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        placeholderMessage = findViewById(R.id.placeholderMessage)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.locations)
        progressBar = findViewById(R.id.progressBar)

        moviesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                moviesSearchPresenter.searchDebounce(
                    changedText = s?.toString() ?: ""
                    //alternative: changedText = queryInput.text.toString()
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }

        textWatcher?.let { queryInput.addTextChangedListener(it) }
        queryInput.addTextChangedListener(textWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
        moviesSearchPresenter.onDestroy()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, DEBOUNCE_DELAY)
        }
        return current
    }

    fun showLoading() {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

//    fun showEmpty(emptyMessage: String) {
//        moviesList.visibility = View.GONE
//        placeholderMessage.visibility = View.VISIBLE
//        progressBar.visibility = View.GONE
//
//        placeholderMessage.text = emptyMessage
//    }

    fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()
    }

    override fun render(state: MoviesState) {
        when {
            state.isLoading -> showLoading()
            state.errorMessage != null -> showError(state.errorMessage)
            else -> showContent(state.movies)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}