package com.test.nrk.moviefinder.di.modules

import com.test.nrk.moviefinder.repository.ImdbMovieRepository
import com.test.nrk.moviefinder.repository.MovieRepository
import com.test.nrk.moviefinder.webservices.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieRepositoryModule {

    @Singleton
    @Provides
    fun getDefaultRepository(imdbWebService: MovieApiService): MovieRepository {
        return ImdbMovieRepository(imdbWebService)
    }
}