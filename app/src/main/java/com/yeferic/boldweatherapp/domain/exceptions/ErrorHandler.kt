package com.yeferic.boldweatherapp.domain.exceptions

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}