package com.test.nrk.moviefinder.helper


sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Failure<T>(val code: Int? = null,val data: T?) : ApiResult<T>()
    object NetworkError : ApiResult<Nothing>()
}