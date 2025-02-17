package com.test.nrk.moviefinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>?>(null)

    val moviesList: StateFlow<List<Movie>?> = _movies

    fun search(query: String) {
        viewModelScope.launch {
            _movies.value = repository.searchMovies(query)
        }
    }
}