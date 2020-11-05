package com.amary.amengsubang.domain.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Error(val message: String? = null) : Resource<Nothing>()
}
