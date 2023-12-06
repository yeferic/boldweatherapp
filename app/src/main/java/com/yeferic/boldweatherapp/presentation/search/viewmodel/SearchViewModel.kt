package com.yeferic.boldweatherapp.presentation.search.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.models.ItemResult
import com.yeferic.boldweatherapp.domain.usecases.search.ClearSearchUseCase
import com.yeferic.boldweatherapp.domain.usecases.search.LocalSearchUseCase
import com.yeferic.boldweatherapp.domain.usecases.search.RemoteSearchUseCase
import com.yeferic.boldweatherapp.presentation.search.states.SearchUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteSearchUseCase: RemoteSearchUseCase,
    private val localSearchUseCase: LocalSearchUseCase,
    private val clearSearchUseCase: ClearSearchUseCase,
) : ViewModel() {
    companion object {
        private const val RANGE_SEARCH_ITEMS: Int = 30
        private const val MIN_TEXT_LENGTH_TO_SEARCH: Int = 3
    }

    private val _uiState = MutableStateFlow<SearchUIState>(SearchUIState.InitState)
    val uiState: StateFlow<SearchUIState> = _uiState

    private val _queryText = MutableLiveData<String>()
    val queryText: LiveData<String> = _queryText

    private val _itemsResult = MutableStateFlow<List<ItemResult>>(arrayListOf())
    val itemsResult: StateFlow<List<ItemResult>> = _itemsResult

    private val _loadingMoreItems = MutableLiveData(false)
    val loadingMoreItems: LiveData<Boolean> = _loadingMoreItems

    private var currentItems: Int = 0

    private var job = Job()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setUIStateAsLoadingState() {
        _uiState.value = SearchUIState.Loading
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setUIStateAsSuccessState(items: List<ItemResult>) {
        _uiState.value = SearchUIState.SuccessSearch(items)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setUIStateAsErrorState(error: String) {
        _uiState.value = SearchUIState.Error(error)
    }

    fun setQueryTextValue(text: String) {
        if (text.isNotEmpty() && text.length > MIN_TEXT_LENGTH_TO_SEARCH) {
            if (_queryText.value != text) {
                _queryText.value = text.trim()
                searchItems()
            }
        } else {
            _uiState.value = SearchUIState.InitState
        }
    }

    fun clearTextAndState() {
        _queryText.value = EMPTY_STRING
        _itemsResult.value = arrayListOf()
        _uiState.value = SearchUIState.InitState
        viewModelScope.launch {
            clearSearchUseCase().collect {
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun searchItems() {
        currentItems = 0
        job.cancelChildren()
        viewModelScope.launch(job) {
            remoteSearchUseCase(
                _queryText.value!!,
                currentItems,
                RANGE_SEARCH_ITEMS,
            ).collect {
                when (it) {
                    is ServiceEventState.Error -> setUIStateAsErrorState("ERROR")
                    ServiceEventState.Loading -> setUIStateAsLoadingState()
                    is ServiceEventState.Success -> {
                        _itemsResult.value = it.data
                        setUIStateAsSuccessState(it.data)
                    }
                }
            }
        }
    }

    fun loadMoreItems() {
        if (_itemsResult.value.size >= RANGE_SEARCH_ITEMS) {
            currentItems += RANGE_SEARCH_ITEMS
            if (_loadingMoreItems.value!!.not()) {
                job.cancelChildren()
                viewModelScope.launch(job) {
                    localSearchUseCase(
                        currentItems,
                        RANGE_SEARCH_ITEMS,
                    ).collect {
                        when (it) {
                            is ServiceEventState.Error -> setUIStateAsErrorState("ERROR")
                            ServiceEventState.Loading -> _loadingMoreItems.postValue(true)
                            is ServiceEventState.Success -> {
                                _itemsResult.value = it.data
                                setUIStateAsSuccessState(it.data)
                            }
                        }
                    }
                }
            }
        }
    }
}
