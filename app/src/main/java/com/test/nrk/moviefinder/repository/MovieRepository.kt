package com.test.nrk.moviefinder.repository

import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.webservices.MovieApiService
import javax.inject.Inject

interface MovieRepository{
    suspend fun searchMovies(query: String): List<Movie>?
}

class ImdbMovieRepository @Inject constructor(private val apiService: MovieApiService) :
    MovieRepository {
    override suspend fun searchMovies(query: String): List<Movie>? {
        return try {
            val response = apiService.searchMovies(query)
            if (response.isSuccessful) response.body()?.movies else null
        } catch (e: Exception) {
            throw Throwable("NETWORK ERROR")
        }
    }
}