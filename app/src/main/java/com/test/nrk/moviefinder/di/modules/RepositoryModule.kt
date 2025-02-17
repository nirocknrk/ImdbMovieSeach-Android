package com.test.nrk.moviefinder.di.modules

import com.test.nrk.moviefinder.repository.ImdbMovieRepository
import com.test.nrk.moviefinder.repository.MovieRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MovieRepositoryModule {

//    fun getDefaultRepository(): MovieRepository {
//        return ImdbMovieRepository()
//    }
}