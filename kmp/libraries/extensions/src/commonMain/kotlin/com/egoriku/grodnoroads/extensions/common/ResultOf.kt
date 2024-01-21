package com.egoriku.grodnoroads.extensions.common

sealed class ResultOf<out T> {
    data class Success<out T>(val value: T) : ResultOf<T>()
    data class Failure(val throwable: Throwable) : ResultOf<Nothing>()
}