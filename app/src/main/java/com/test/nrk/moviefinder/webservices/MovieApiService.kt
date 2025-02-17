package com.test.nrk.moviefinder.webservices

import com.test.nrk.moviefinder.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = "8d6aa4ca"
    ): Response<MovieResponse>
}