package com.test.nrk.moviefinder.di.modules

import com.test.nrk.moviefinder.BuildConfig
import com.test.nrk.moviefinder.webservices.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieWebServiceModule {

    @Singleton
    @Provides
    fun getImdbMovieWebService(): MovieApiService {


        val httpClient = getBasicHttpClientBuilder()
            .addInterceptor(getHttpLogInterceptor())
            .addInterceptor(getApiKeyInterceptor())
            .build()

        val retrofitBuilder = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())

        return retrofitBuilder.baseUrl("https://www.omdbapi.com/")
            .build()
            .create(MovieApiService::class.java)
    }

    fun getBasicHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

    }


    fun getHttpLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    fun getApiKeyInterceptor(): Interceptor{
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            // Add the query parameter (e.g., `apikey` for OMDb API)
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("apikey", BuildConfig.IMDB_APIKEY) // Add your API key
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }
}
