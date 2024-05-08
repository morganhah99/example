package com.sample.myapplication.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.myapplication.repository.MarvelRepository
import com.sample.myapplication.CharacterResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Why
 * View from data
 * Business logic
 * survive configuration changes: orientation, locale, keybaord
 * lifecycle aware component
 *
 * 1 Inject repository
 * 2 Create LiveData : Observer
 * 3 Coroutines to call suspend functions : Scopes- Dispatchers
 *
 */

@HiltViewModel
class CharacterViewModel @Inject constructor(private val marvelRepository: MarvelRepository) : ViewModel(){

    // val marvelLiveData = MutableLiveData<CharacterResponseModel>()
    // val errorLivedata = MutableLiveData<String>()
    override fun onCleared() {
        viewModelScope.cancel()
    }

    /**
     * Stateflow always required initial state or value to be passed as paramater
     * Livedata = no  state or value to be passed
     *
     */
   // private val _characterResponseLiveData = MutableLiveData<ApiResponse<CharacterResponseModel>>()
  //  val characterResponse :LiveData<ApiResponse<CharacterResponseModel>> = _characterResponse

    private val _characterResponseFlow = MutableStateFlow<ApiResponse<CharacterResponseModel>>(ApiResponse.LoadingState())
    val characterResponse: StateFlow<ApiResponse<CharacterResponseModel>> = _characterResponseFlow



    // livedata: Observer, itertor
    // stateflow: reactive , functional

    // backpressure handling
    // handling data
    // lifwcycle



    @SuppressLint("SuspiciousIndentation")
    fun fetchMarvelCharacter(){
        viewModelScope.launch (Dispatchers.IO){
            //_characterResponse.postValue(ApiResponse.LoadingState())
        val response = marvelRepository.getAllCharacters()
            //200 or 404
             if (response.isSuccessful){
                // marvelLiveData.postValue(response.body())

                     _characterResponseFlow.value = ApiResponse.SuccessState(response.body()!!)

               //  _characterResponse.postValue(ApiResponse.SuccessState(response.body()!!))
             }
            else {
                 _characterResponseFlow.value = ApiResponse.ErrorState(response.errorBody().toString())

                 //_characterResponse.postValue(ApiResponse.ErrorState(response.errorBody().toString()))
                //errorLivedata.postValue(response.errorBody().toString())
                 Log.i("Data_Marvel", response.body().toString())

             }

        }
    }

}