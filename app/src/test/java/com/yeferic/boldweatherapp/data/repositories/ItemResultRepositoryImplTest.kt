package com.yeferic.boldweatherapp.data.repositories

import com.yeferic.boldweatherapp.data.sources.local.dao.ItemResultDao
import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.data.sources.remote.dto.ItemResultDto
import com.yeferic.boldweatherapp.data.sources.remote.dto.mapToDomain
import com.yeferic.boldweatherapp.domain.models.ItemResult
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
class ItemResultRepositoryImplTest {
    private lateinit var repository: ItemResultRepositoryImpl

    @Mock
    private lateinit var apiMock: ItemResultApi

    @Mock
    private lateinit var itemResultDaoMock: ItemResultDao

    @Before
    fun setupRepository() {
        repository = ItemResultRepositoryImpl(itemResultDaoMock, apiMock)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(itemResultDaoMock)
        verifyNoMoreInteractions(apiMock)
    }

    @Test
    fun search_invoked_returnsValue() = runTest {
        // given
        val query = "Florencia"

        val response = listOf(ItemResultDto.emptyObject())
        given(apiMock.search(query)).willReturn(
            Response.success(response),
        )

        // when
        val result = repository.search(query)

        // then
        TestCase.assertEquals(result, response.map { it.mapToDomain() })
        verify(apiMock).search(query)
    }

    @Test
    fun saveItems_invoked_callsToDao() = runTest {
        // given
        val items = emptyList<ItemResult>()

        // when
        repository.saveItems(items)

        // then
        verify(itemResultDaoMock).saveItems(items)
    }

    @Test
    fun getItems_invoked_returnsValue() = runTest {
        // given
        val from = 0
        val to = 10

        val response = listOf(ItemResultDto.emptyObject().mapToDomain())
        given(itemResultDaoMock.getItems()).willReturn(
            response,
        )

        // when
        val result = repository.getItems(from, to)

        // then
        TestCase.assertEquals(result, response)
        TestCase.assertTrue(result.size == response.size)
        verify(itemResultDaoMock).getItems()
    }

    @Test
    fun removeItems_invoked_callsToDao() = runTest {
        // given

        // when
        repository.removeItems()

        // then
        verify(itemResultDaoMock).deleteItems()
    }
}
