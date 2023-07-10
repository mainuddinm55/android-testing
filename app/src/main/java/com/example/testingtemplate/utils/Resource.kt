package com.example.testingtemplate.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failed(val msg: String?) : Resource<Nothing>()
}
