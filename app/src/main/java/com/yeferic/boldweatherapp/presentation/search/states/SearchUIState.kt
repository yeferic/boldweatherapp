package com.yeferic.boldweatherapp.presentation.search.states

import com.yeferic.boldweatherapp.domain.models.ItemResult

sealed class SearchUIState {
    object InitState : SearchUIState()
    object Loading : SearchUIState()

    data class SuccessSearch(val data: List<ItemResult>) : SearchUIState()
    data class Error(val text: String) : SearchUIState()
}
