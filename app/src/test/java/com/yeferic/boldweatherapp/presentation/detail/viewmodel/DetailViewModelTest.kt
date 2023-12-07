package com.yeferic.boldweatherapp.presentation.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeferic.boldweatherapp.common.MainDispatcherRule
import com.yeferic.boldweatherapp.common.getPrivatePropertyValue
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.exceptions.ErrorEntity
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.usecases.detail.GetItemDetailUseCase
import com.yeferic.boldweatherapp.presentation.detail.states.DetailUIState
import com.yeferic.boldweatherapp.presentation.detail.viewmodel.DetailViewModel.Companion.DAYS
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
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    companion object {
        private const val ITEM_DETAIL_FIELD = "_itemDetail"
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var getItemDetailUseCaseMock: GetItemDetailUseCase

    @Before
    fun setup() {
        viewModel = DetailViewModel(getItemDetailUseCaseMock)
    }

    @Test
    fun setUIStateAsLoadingState_invoked_setsState() = runTest {
        // given

        // when
        viewModel.setUIStateAsLoadingState()
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, DetailUIState.Loading)
    }

    @Test
    fun setUIStateAsErrorState_invoked_setsState() = runTest {
        // given
        val error = "Error"

        // when
        viewModel.setUIStateAsErrorState(error)
        val result = viewModel.uiState.value

        // then
        TestCase.assertEquals(result, DetailUIState.Error(error))
    }

    @Test
    fun getItemDetail_invoked_returnsError() = runTest {
        // given
        val name = "Florencia"

        given(getItemDetailUseCaseMock(name, DAYS)).willReturn(
            flow { emit(ServiceEventState.Error(ErrorEntity.Network)) },
        )

        // when
        viewModel.getItemDetail(name)
        val uiState = viewModel.uiState.value

        // then
        TestCase.assertEquals(uiState, DetailUIState.Error(EMPTY_STRING))
        verify(getItemDetailUseCaseMock).invoke(name, DAYS)
        verifyNoMoreInteractions(getItemDetailUseCaseMock)
    }

    @Test
    fun getItemDetail_invoked_returnsSuccess() = runTest {
        // given
        val name = "Florencia"
        val detailResponse = ItemDetail()
        given(getItemDetailUseCaseMock(name, DAYS)).willReturn(
            flow { emit(ServiceEventState.Success(detailResponse)) },
        )

        // when
        viewModel.getItemDetail(name)
        val productDetail =
            getPrivatePropertyValue<MutableStateFlow<ItemDetail>>(viewModel, ITEM_DETAIL_FIELD)
        val uiState = viewModel.uiState.value

        // then
        TestCase.assertEquals(uiState, DetailUIState.Success(detailResponse))
        TestCase.assertEquals(productDetail.value, detailResponse)
        verify(getItemDetailUseCaseMock).invoke(name, DAYS)
        verifyNoMoreInteractions(getItemDetailUseCaseMock)
    }
}
