package com.example.baseproyect.domain.exeptions

sealed class ResultState<out T> {
    data object Start : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Failure(val throwable: Throwable) : ResultState<Nothing>()
}