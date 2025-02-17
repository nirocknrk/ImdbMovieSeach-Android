package com.test.nrk.moviefinder

import com.google.common.truth.Truth.assertThat
import com.test.nrk.moviefinder.helper.ApiResult
import com.test.nrk.moviefinder.model.Movie
import com.test.nrk.moviefinder.model.MovieResponse
import com.test.nrk.moviefinder.repository.ImdbMovieRepository
import com.test.nrk.moviefinder.until.MainCoroutineRule
import com.test.nrk.moviefinder.webservices.MovieApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class ImdbMovieRepositoryTest {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private lateinit var repository: ImdbMovieRepository
    private val mockApiService: MovieApiService = mockk()

    @Before
    fun setUp() {
        repository = ImdbMovieRepository(mockApiService)
    }

    @Test
    fun `searchMovies returns success when API response is successful`() = runTest {
        // Given
        val mockResponse = Response.success(MovieResponse(listOf(Movie("1","Inception", "2010", "Movie", "Poster\": \"https://m.media-amazon.com/images/M/MV5BNTU3MzNjZTctYzE3ZC00Y2I1LWFlYTgtYWY2ZTNhYTE4NTJmXkEyXkFqcGc@._V1_SX300.jpg"))))
        coEvery { mockApiService.searchMovies("Inception") } returns mockResponse

        // When
        val result = repository.searchMovies("Inception")

        // Then
        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        val successResult = result as ApiResult.Success
        assertThat(successResult.data).isNotNull()
        assertThat(successResult.data?.first()?.title).isEqualTo("Inception")
    }

    @Test
    fun `searchMovies returns failure when API response fails`() = runTest {
        // Given
        coEvery { mockApiService.searchMovies("Unknown") } throws HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))

        // When
        val result = repository.searchMovies("Unknown")

        // Then
        assertThat(result).isInstanceOf(ApiResult.Failure::class.java)
        val failureResult = result as ApiResult.Failure
        assertThat(failureResult.code).isEqualTo(404)
    }

    @Test
    fun `searchMovies returns network error when IOException occurs`() = runTest {
        // Given
        coEvery { mockApiService.searchMovies("Test") } throws IOException()

        // When
        val result = repository.searchMovies("Test")

        // Then
        assertThat(result).isInstanceOf(ApiResult.Failure::class.java)
        val failureResult = result as ApiResult.Failure
        assertThat(failureResult.code).isEqualTo(-1)
    }
}
