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
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieWebServiceModule {

    @Singleton
    @Provides
    @Named("NEWCmsService")
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

    private fun getApiKeyInterceptor(): Interceptor {
        return Interceptor() { chain ->
            var request = chain.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter("apikey", BuildConfig.IMDB_APIKEY)
                .build()

            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url).build()
            chain.proceed(request)
        }
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
}
