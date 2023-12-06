package com.yeferic.boldweatherapp.domain.usecases.search

import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import com.yeferic.boldweatherapp.domain.models.ItemResult
import com.yeferic.boldweatherapp.domain.repositories.ItemResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class RemoteSearchUseCase @Inject constructor(
    private val itemResultRepository: ItemResultRepository,
    private val errorHandler: ErrorHandler,
) {
    suspend operator fun invoke(
        query: String,
        from: Int,
        to: Int,
    ): Flow<ServiceEventState<List<ItemResult>>> = flow {
        try {
            emit(ServiceEventState.Loading)
            val result = itemResultRepository.search(query)
            itemResultRepository.saveItems(result)
            val items = itemResultRepository.getItems(from, to)
            emit(ServiceEventState.Success(items))
        } catch (e: Exception) {
            emit(ServiceEventState.Error(errorHandler.getError(e)))
        }
    }
}
