package com.amary.amengsubang.core.data.datasource.remote.network

sealed class NetworkResponse<out R> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>()
    object Empty : NetworkResponse<Nothing>()
}