package com.example.psicoachproject.core

sealed class Resource<out T: Any> {
    object Loading : Resource<Nothing>()
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
}