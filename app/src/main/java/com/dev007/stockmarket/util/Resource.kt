package com.dev007.stockmarket.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(msg: String, data: T? = null) : Resource<T>(data, msg)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()


}
