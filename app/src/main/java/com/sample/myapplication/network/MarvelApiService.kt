package com.sample.myapplication.network

import com.sample.myapplication.CharacterResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiService {
    @GET("public/characters?ts=1&apikey=678c0bb6d6aea2fbde954e9c8e170586&hash=6c5502e0089e46828d91a0d16149b185")
    suspend fun getAllList(): Response<CharacterResponseModel>
}