package com.example.mymusicappcompose.core


sealed interface Result<out TYPE> {
    data class Success<out TYPE>(val data: TYPE) : Result<TYPE>
    data class Error(val error: Throwable): Result<Nothing>
}
