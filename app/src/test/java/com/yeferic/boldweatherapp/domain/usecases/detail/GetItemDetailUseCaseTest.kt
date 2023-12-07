package com.yeferic.boldweatherapp.domain.usecases.detail

import com.google.gson.JsonParseException
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.domain.exceptions.ErrorEntity
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.repositories.ItemDetailRepository
import junit.framework.TestCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class GetItemDetailUseCaseTest {
    private lateinit var useCase: GetItemDetailUseCase

    @Mock
    private lateinit var itemDetailRepositoryMock: ItemDetailRepository

    @Mock
    private lateinit var errorHandlerMock: ErrorHandler

    @Before
    fun setup() {
        useCase = GetItemDetailUseCase(itemDetailRepositoryMock, errorHandlerMock)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(itemDetailRepositoryMock, errorHandlerMock)
    }

    @Test
    fun getItemDetailUseCase_invoked_returnsValue() = runTest {
        // given
        val name = "Florencia"
        val days = 3
        val itemDetail = ItemDetail()
        given(itemDetailRepositoryMock.getDetailItem(name, days)).willReturn(
            itemDetail,
        )

        // when
        val flows = useCase(name, days).toList()

        // then
        TestCase.assertEquals(flows.size, 2)
        TestCase.assertEquals(flows[0], ServiceEventState.Loading)
        TestCase.assertEquals(flows[1], ServiceEventState.Success(itemDetail))
        verify(itemDetailRepositoryMock).getDetailItem(name, days)
        verifyNoInteractions(errorHandlerMock)
    }

    @Test
    fun getItemDetailUseCase_invoked_returnsError() = runTest {
        // given
        val name = "Florencia"
        val days = 3
        given(errorHandlerMock.getError(any())).willReturn(
            ErrorEntity.Unknown,
        )
        given(itemDetailRepositoryMock.getDetailItem(name, days)).willThrow(
            JsonParseException(Throwable()),
        )

        // when
        val flows = useCase(name, days).toList()

        // then
        TestCase.assertEquals(flows.size, 2)
        TestCase.assertEquals(flows[0], ServiceEventState.Loading)
        TestCase.assertEquals(flows[1], ServiceEventState.Error(ErrorEntity.Unknown))
        verify(itemDetailRepositoryMock).getDetailItem(name, days)
        verify(errorHandlerMock).getError(any())
    }
}
