package com.moabo.moviedemo.di


import android.app.Application
import androidx.room.Room
import com.moabo.moviedemo.BuildConfig
import com.moabo.moviedemo.model.MovieDatabase
import com.moabo.moviedemo.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: MovieDatabase.Callback
    ) = Room.databaseBuilder(app, MovieDatabase::class.java, "movie_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideTaskDao(db: MovieDatabase) = db.movieDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
