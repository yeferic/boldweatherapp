package com.yeferic.boldweatherapp.domain.usecases.search

import com.google.gson.JsonParseException
import com.yeferic.boldweatherapp.core.commons.ServiceEventState
import com.yeferic.boldweatherapp.data.sources.remote.dto.ItemResultDto
import com.yeferic.boldweatherapp.data.sources.remote.dto.mapToDomain
import com.yeferic.boldweatherapp.domain.exceptions.ErrorEntity
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import com.yeferic.boldweatherapp.domain.repositories.ItemResultRepository
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
class LocalSearchUseCaseTest {
    private lateinit var useCase: LocalSearchUseCase

    @Mock
    private lateinit var itemResultRepositoryMock: ItemResultRepository

    @Mock
    private lateinit var errorHandlerMock: ErrorHandler

    @Before
    fun setup() {
        useCase = LocalSearchUseCase(itemResultRepositoryMock, errorHandlerMock)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(itemResultRepositoryMock, errorHandlerMock)
    }

    @Test
    fun localSearchUseCase_invoked_returnsValue() = runTest {
        // given
        val from = 0
        val to = 10
        val itemsResponse = listOf(ItemResultDto.emptyObject().mapToDomain())
        given(itemResultRepositoryMock.getItems(from, to)).willReturn(
            itemsResponse,
        )

        // when
        val flows = useCase(from, to).toList()

        // then
        TestCase.assertEquals(flows.size, 2)
        TestCase.assertEquals(flows[0], ServiceEventState.Loading)
        TestCase.assertEquals(flows[1], ServiceEventState.Success(itemsResponse))
        verify(itemResultRepositoryMock).getItems(from, to)
        verifyNoInteractions(errorHandlerMock)
    }

    @Test
    fun localSearchUseCase_invoked_returnsError() = runTest {
        // given
        val from = 0
        val to = 10
        given(errorHandlerMock.getError(any())).willReturn(
            ErrorEntity.Unknown,
        )
        given(itemResultRepositoryMock.getItems(from, to)).willThrow(
            JsonParseException(Throwable()),
        )

        // when
        val flows = useCase(from, to).toList()

        // then
        TestCase.assertEquals(flows.size, 2)
        TestCase.assertEquals(flows[0], ServiceEventState.Loading)
        TestCase.assertEquals(flows[1], ServiceEventState.Error(ErrorEntity.Unknown))
        verify(itemResultRepositoryMock).getItems(from, to)
        verify(errorHandlerMock).getError(any())
    }
}
