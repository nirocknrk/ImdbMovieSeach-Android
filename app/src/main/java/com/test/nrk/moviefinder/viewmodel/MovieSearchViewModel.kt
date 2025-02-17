package com.test.nrk.moviefinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.nrk.moviefinder.helper.ApiResult
import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>?>(null)

    val moviesList: StateFlow<List<Movie>?> = _movies

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _searchInprogress = MutableStateFlow(false)
    val searchProgress = _searchInprogress.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _searchInprogress.value = true
            when (val response = repository.searchMovies(query)) {
                is ApiResult.Success -> {
                    _movies.value = response.data
                }

                is ApiResult.Failure -> {
                    _errorMessage.value = "Error ${response.code}"
                }

                is ApiResult.NetworkError -> {
                    _errorMessage.value = "Network Error"
                }
            }
            _searchInprogress.value = false
        }
    }

}