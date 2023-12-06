package com.yeferic.boldweatherapp.presentation.detail.states

import com.yeferic.boldweatherapp.domain.models.ItemDetail

sealed class DetailUIState {
    object InitState : DetailUIState()
    object Loading : DetailUIState()
    data class Success(val data: ItemDetail) : DetailUIState()
    data class Error(val text: String) : DetailUIState()
}
