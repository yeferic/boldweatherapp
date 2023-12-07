package com.yeferic.boldweatherapp.data.repositories

import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.models.Location
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ItemDetailRepositoryImplTest {
    private lateinit var repository: ItemDetailRepositoryImpl

    @Mock
    private lateinit var apiMock: ItemResultApi

    @Before
    fun setupRepository() {
        repository = ItemDetailRepositoryImpl(apiMock)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(apiMock)
    }

    @Test
    fun getDetailItem_invoked_returnsValue() = runTest {
        // given
        val name = "name"
        val days = 1

        val response = ItemDetail(location = Location(name = name))
        given(apiMock.detail(name, days)).willReturn(
            Response.success(response),
        )

        // when
        val result = repository.getDetailItem(name, days)

        // then
        TestCase.assertEquals(result, response)
        verify(apiMock).detail(name, days)
    }
}
