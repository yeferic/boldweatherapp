package com.yeferic.boldweatherapp.core.commons

import com.yeferic.boldweatherapp.domain.exceptions.ErrorEntity

sealed class ServiceEventState<out R> {
    data class Success<out T>(val data: T) : ServiceEventState<T>()
    data class Error(val errorEntity: ErrorEntity) : ServiceEventState<Nothing>()
    object Loading : ServiceEventState<Nothing>()
}
