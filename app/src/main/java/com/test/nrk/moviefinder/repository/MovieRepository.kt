package com.test.nrk.moviefinder.repository

import com.test.nrk.moviefinder.helper.ApiResult
import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.webservices.MovieApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface MovieRepository {
    suspend fun searchMovies(query: String): ApiResult<List<Movie>?>
}

class ImdbMovieRepository @Inject constructor(private val apiService: MovieApiService) :
    MovieRepository {
    override suspend fun searchMovies(query: String): ApiResult<List<Movie>?> {
        return try {
            val response = apiService.searchMovies(query)
            ApiResult.Success(if (response.isSuccessful) response.body()?.movies else null)
        } catch (throwable: Exception) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    ApiResult.Failure(code, null)
                }
                is IOException -> ApiResult.Failure(-1, null)
                else -> {
                    ApiResult.NetworkError
                }
            }
        }
    }
}