package com.moabo.moviedemo.di


import com.moabo.moviedemo.BuildConfig
import com.moabo.moviedemo.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor = Interceptor { chain ->
        val url = chain.request().url.newBuilder().addQueryParameter("api_key",
            BuildConfig.THE_MOVIE_DB_API_KEY).addQueryParameter("language" ,"en-US").build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()


        @Provides
        fun providesUrl() = "https://api.themoviedb.org/3/"

        @Provides
        @Singleton
        fun providesApiService(url:String) : ApiService =
            Retrofit.Builder()
                .baseUrl(url)
                .client(apiClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
