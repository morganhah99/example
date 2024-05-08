package com.sample.myapplication.repository


import android.app.Application
import android.content.Context
import com.sample.myapplication.network.MarvelApiService
import com.sample.myapplication.network.interceptors.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.cache.CacheInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MarvelModule @Inject constructor() {
    /**
     * MarvelApiService : passed as a paramter to repository
     * MarvelRepository : passed as a paramter to viewmodel
     */
    /**
     * Todo: Caching : expiration, size
     * State management
     */

    /**
     *  Authentication headers
     *  Logging
     *  Certificate pinning
     *  Caching
     *  Retries
     */

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideApiServiceObject(context: Context): MarvelApiService {
        /**
         * where to store - place - file
         * how much  : size : MB
         * how long : time
         */
        val cacheSize = 20L * 1024L * 1024L // 20 MB
        val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)


        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(com.sample.myapplication.network.interceptors.CacheInterceptor())
            .addNetworkInterceptor(com.sample.myapplication.network.interceptors.CacheInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideMarvelRepo(marvelApiService: MarvelApiService): MarvelRepository {
        return MarvelRepository(marvelApiService)
    }
}
