package com.yeferic.boldweatherapp.presentation.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yeferic.boldweatherapp.common.MainDispatcherRule
import com.yeferic.boldweatherapp.common.getPrivatePropertyValue
import com.yeferic.boldweatherapp.common.setPrivatePropertyValue
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.data.sources.remote.dto.ItemResultDto
import com.yeferic.boldweatherapp.data.sources.remote.dto.mapToDomain
import com.yeferic.boldweatherapp.domain.exceptions.ErrorEntity
import com.yeferic.boldweatherapp.domain.models.ItemResult
import com.yeferic.boldweatherapp.domain.usecases.search.ClearSearchUseCase
import com.yeferic.boldweatherapp.domain.usecases.search.LocalSearchUseCase
import com.yeferic.boldweatherapp.domain.usecases.search.RemoteSearchUseCase
import com.yeferic.boldweatherapp.presentation.search.states.SearchUIState
import com.yeferic.boldweatherapp.presentation.search.viewmodel.SearchViewModel.Companion.RANGE_SEARCH_ITEMS
import com.yeferic.boldweatherapp.presentation.search.viewmodel.SearchViewModel.Companion.SEARCH_ERROR
import junit.framework.TestCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    companion object {
        private const val QUERY_TEXT_FIELD = "_queryText"
        private const val ITEM_RESULT_FIELD = "_itemsResult"
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var remoteSearchUseCaseMock: RemoteSearchUseCase

    @Mock
    private lateinit var localSearchUseCaseMock: LocalSearchUseCase

    @Mock
    private lateinit var clearSearchUseCaseMock: ClearSearchUseCase

    @Before
    fun setup() {
        viewModel =
            SearchViewModel(remoteSearchUseCaseMock, localSearchUseCaseMock, clearSearchUseCaseMock)
    }

    @Test
    fun setUIStateAsLoadingState_invoked_setsState() = runTest {
        // given

        // when
        viewModel.setUIStateAsLoadingState()
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, SearchUIState.Loading)
        verifyNoInteractions(
            remoteSearchUseCaseMock,
            localSearchUseCaseMock,
            clearSearchUseCaseMock,
        )
    }

    @Test
    fun setUIStateAsErrorState_invoked_setsState() = runTest {
        // given
        val error = "Error"

        // when
        viewModel.setUIStateAsErrorState(error)
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, SearchUIState.Error(error))
        verifyNoInteractions(
            remoteSearchUseCaseMock,
            localSearchUseCaseMock,
            clearSearchUseCaseMock,
        )
    }

    @Test
    fun setUIStateAsSuccessState_invoked_setsState() = runTest {
        // given
        val items = listOf(ItemResultDto.emptyObject().mapToDomain())

        // when
        viewModel.setUIStateAsSuccessState(items)
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, SearchUIState.SuccessSearch(items))
        verifyNoInteractions(
            remoteSearchUseCaseMock,
            localSearchUseCaseMock,
            clearSearchUseCaseMock,
        )
    }

    @Test
    fun setQueryTextValue_invoked_setsEmptyValue() = runTest {
        // given
        val text = String()

        // when
        viewModel.setQueryTextValue(text)
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, SearchUIState.InitState)
        verifyNoInteractions(
            remoteSearchUseCaseMock,
            localSearchUseCaseMock,
            clearSearchUseCaseMock,
        )
    }

    @Test
    fun setQueryTextValue_invoked_setsValue() = runTest {
        // given
        val text = "Florencia"
        given(remoteSearchUseCaseMock.invoke(any(), any(), any())).willReturn(
            flow { emit(ServiceEventState.Loading) },
        )

        // when
        viewModel.setQueryTextValue(text)

        val queryText =
            getPrivatePropertyValue<MutableLiveData<String>>(viewModel, QUERY_TEXT_FIELD)

        // then
        TestCase.assertEquals(queryText.value, text)
        verifyNoInteractions(localSearchUseCaseMock, clearSearchUseCaseMock)
    }

    @Test
    fun clearTextAndState_invoked_setsEmptyValues() = runTest {
        // given
        given(clearSearchUseCaseMock.invoke()).willReturn(
            flow { emit(ServiceEventState.Success(true)) },
        )

        // when
        viewModel.clearTextAndState()
        val uiState = viewModel.uiState.value

        val queryText =
            getPrivatePropertyValue<MutableLiveData<String>>(viewModel, QUERY_TEXT_FIELD)

        val itemResult = getPrivatePropertyValue<MutableStateFlow<List<ItemResult>>>(
            viewModel,
            ITEM_RESULT_FIELD,
        )

        // then
        TestCase.assertEquals(queryText.value, EMPTY_STRING)
        TestCase.assertEquals(uiState, SearchUIState.InitState)
        TestCase.assertEquals(itemResult.value, arrayListOf<ItemResult>())
        verifyNoInteractions(remoteSearchUseCaseMock, localSearchUseCaseMock)
    }

    @Test
    fun searchItems_invoked_callsToUseCaseLoadingState() = runTest {
        // given
        val text = "Florencia"
        val currentItems = 0
        given(remoteSearchUseCaseMock.invoke(text, currentItems, RANGE_SEARCH_ITEMS)).willReturn(
            flow { emit(ServiceEventState.Loading) },
        )
        setPrivatePropertyValue(viewModel, QUERY_TEXT_FIELD, MutableLiveData(text))

        // when
        viewModel.searchItems()
        val uiState = viewModel.uiState.value

        // then
        TestCase.assertEquals(uiState, SearchUIState.Loading)
        verify(remoteSearchUseCaseMock).invoke(text, currentItems, RANGE_SEARCH_ITEMS)
        verifyNoMoreInteractions(remoteSearchUseCaseMock)
        verifyNoInteractions(localSearchUseCaseMock, clearSearchUseCaseMock)
    }

    @Test
    fun searchItems_invoked_callsToUseCaseErrorState() = runTest {
        // given
        val text = "Florencia"
        val currentItems = 0
        given(remoteSearchUseCaseMock.invoke(text, currentItems, RANGE_SEARCH_ITEMS)).willReturn(
            flow { emit(ServiceEventState.Error(ErrorEntity.Unknown)) },
        )
        setPrivatePropertyValue(viewModel, QUERY_TEXT_FIELD, MutableLiveData(text))

        // when
        viewModel.searchItems()
        val uiState = viewModel.uiState.value

        // then
        TestCase.assertEquals(uiState, SearchUIState.Error(SEARCH_ERROR))
        verify(remoteSearchUseCaseMock).invoke(text, currentItems, RANGE_SEARCH_ITEMS)
        verifyNoMoreInteractions(remoteSearchUseCaseMock)
        verifyNoInteractions(localSearchUseCaseMock, clearSearchUseCaseMock)
    }

    @Test
    fun searchItems_invoked_callsToUseCaseSuccessState() = runTest {
        // given
        val text = "Florencia"
        val currentItems = 0
        val items = listOf(ItemResultDto.emptyObject().mapToDomain())
        given(remoteSearchUseCaseMock.invoke(text, currentItems, RANGE_SEARCH_ITEMS)).willReturn(
            flow { emit(ServiceEventState.Success(items)) },
        )
        setPrivatePropertyValue(viewModel, QUERY_TEXT_FIELD, MutableLiveData(text))

        // when
        viewModel.searchItems()
        val uiState = viewModel.uiState.value

        // then
        TestCase.assertEquals(uiState, SearchUIState.SuccessSearch(items))
        verify(remoteSearchUseCaseMock).invoke(text, currentItems, RANGE_SEARCH_ITEMS)
        verifyNoMoreInteractions(remoteSearchUseCaseMock)
        verifyNoInteractions(localSearchUseCaseMock, clearSearchUseCaseMock)
    }
}
