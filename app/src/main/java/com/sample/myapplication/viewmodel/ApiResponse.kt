package com.sample.myapplication.viewmodel

import com.sample.myapplication.CharacterResponseModel

/**
 * State management:
 * Loading: Progress Dialog
 * Loaded: Success: data received
 * Error: Error message
 */
sealed class ApiResponse<out T> {
    data class SuccessState<out T>(val data: T): ApiResponse<T>()
    data class ErrorState(val message: String): ApiResponse<Nothing>()
    data class LoadingState<out T>(val message: String? = null) : ApiResponse<T>()

}
