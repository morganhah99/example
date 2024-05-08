package com.sample.myapplication.network.interceptors

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        /**
         * how long
         */
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.HOURS) // 1 minute cache
            .build()

        // Log a message when data is read from the cache
        val cachedResponse = response.cacheResponse
        if (cachedResponse != null) {
            Log.d("MarvelModule", "Data retrieved from cache"+ cachedResponse.message.toString())
        }else {
            Log.d("MarvelModule", "Data retrieved from API")
        }

        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}