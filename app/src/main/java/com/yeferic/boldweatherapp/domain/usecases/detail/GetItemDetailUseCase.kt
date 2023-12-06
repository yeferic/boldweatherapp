package com.yeferic.boldweatherapp.domain.usecases.detail

import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.repositories.ItemDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GetItemDetailUseCase @Inject constructor(
    private val itemDetailRepository: ItemDetailRepository,
    private val errorHandler: ErrorHandler,
) {
    suspend operator fun invoke(
        name: String,
        days: Int,
    ): Flow<ServiceEventState<ItemDetail>> = flow {
        try {
            emit(ServiceEventState.Loading)
            val result = itemDetailRepository.getDetailItem(name, days)
            emit(ServiceEventState.Success(result))
        } catch (e: Exception) {
            emit(ServiceEventState.Error(errorHandler.getError(e)))
        }
    }
}
