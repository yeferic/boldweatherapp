package com.yeferic.boldweatherapp.presentation.detail.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.usecases.detail.GetItemDetailUseCase
import com.yeferic.boldweatherapp.presentation.detail.states.DetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getItemDetailUseCase: GetItemDetailUseCase,
) : ViewModel() {
    companion object {
        private const val DAYS: Int = 3
    }

    private val _uiState = MutableStateFlow<DetailUIState>(DetailUIState.InitState)
    val uiState: StateFlow<DetailUIState> = _uiState

    private val _itemDetail = MutableStateFlow(ItemDetail())
    val itemDetail: StateFlow<ItemDetail> = _itemDetail

    fun getProductDetail(name: String) {
        viewModelScope.launch {
            getItemDetailUseCase(name, DAYS)
                .collect {
                    when (it) {
                        ServiceEventState.Loading -> setUIStateAsLoadingState()
                        is ServiceEventState.Error -> setUIStateAsErrorState(EMPTY_STRING)
                        is ServiceEventState.Success -> {
                            _itemDetail.value = it.data
                            _uiState.value = DetailUIState.Success(it.data)
                        }
                    }
                }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setUIStateAsLoadingState() {
        _uiState.value = DetailUIState.Loading
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setUIStateAsErrorState(error: String) {
        _uiState.value = DetailUIState.Error(error)
    }
}
