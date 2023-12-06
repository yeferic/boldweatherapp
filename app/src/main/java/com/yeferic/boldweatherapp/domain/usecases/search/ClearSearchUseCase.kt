package com.yeferic.boldweatherapp.domain.usecases.search

import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import com.yeferic.boldweatherapp.domain.repositories.ItemResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class ClearSearchUseCase @Inject constructor(
    private val itemResultRepository: ItemResultRepository,
    private val errorHandler: ErrorHandler,
) {
    suspend operator fun invoke(): Flow<ServiceEventState<Boolean>> = flow {
        itemResultRepository.removeItems()
        emit(ServiceEventState.Success(true))
    }
}
