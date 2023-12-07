package com.yeferic.boldweatherapp.domain.usecases.search

import com.yeferic.boldweatherapp.core.commons.ServiceEventState
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class ClearSearchUseCaseTest {
    private lateinit var useCase: ClearSearchUseCase

    @Mock
    private lateinit var itemResultRepositoryMock: ItemResultRepository

    @Mock
    private lateinit var errorHandlerMock: ErrorHandler

    @Before
    fun setup() {
        useCase = ClearSearchUseCase(itemResultRepositoryMock, errorHandlerMock)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(itemResultRepositoryMock, errorHandlerMock)
    }

    @Test
    fun clearSearchUseCase_invoked_returnsValue() = runTest {
        // given

        // when
        val flows = useCase().toList()

        // then
        TestCase.assertEquals(flows.size, 1)

        TestCase.assertEquals(flows[0], ServiceEventState.Success(true))
        verify(itemResultRepositoryMock).removeItems()
        verifyNoInteractions(errorHandlerMock)
    }
}
