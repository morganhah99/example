package com.sample.myapplication.repository

import com.sample.myapplication.network.MarvelApiService
import javax.inject.Inject


class MarvelRepository @Inject constructor(private val service: MarvelApiService){
    suspend fun getAllCharacters () = service.getAllList()

    /**
     * store the response in localdata base
     */
}