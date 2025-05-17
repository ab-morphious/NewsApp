package com.appsolutely.newsapp.presentation.viewmodel

import com.appsolutely.newsapp.common.ErrorType
import com.appsolutely.newsapp.common.Result
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.domain.model.Source
import com.appsolutely.newsapp.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private lateinit var getNewsUseCase: GetNewsUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val testQuery = "Messi"
    private val testDate = "2025-14-09"
    private val testSortBy = "publishedAt"

    private val mockNews = listOf(
        News(
            source = Source(id = "1", name = "Test Source"),
            author = "Test Author",
            title = "Test Title",
            description = "Test Description",
            url = "https://test.com",
            imageUrl = "https://test.com/image.jpg",
            publishedAt = "2024-03-14",
            content = "Test Content"
        ),
        News(
            source = Source(id = "2", name = "Test Source"),
            author = "Test Author2",
            title = "Test Title2",
            description = "Test Description2",
            url = "https://test.com2",
            imageUrl = "https://test.com/image.jpg2",
            publishedAt = "2024-03-14",
            content = "Test Content2"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getNewsUseCase = mock()
        viewModel = NewsViewModel(getNewsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getNews succeeds, should emit Success state with news list`() = runTest {
        // Given
        doAnswer { Result.Success(mockNews) }.`when`(getNewsUseCase).invoke(testQuery, testDate, testSortBy)

        // When
        viewModel.getNews(testQuery, testDate, testSortBy)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.newsState.first()
        assertTrue(state is NewsUiState.Success)
        assertEquals(mockNews, (state as NewsUiState.Success).news)
    }

    @Test
    fun `when getNews fails, should emit Error state with message`() = runTest {
        // Given
        val errorMessage = "Network error"
        doAnswer { 
            Result.Error(
                errorType = ErrorType.NetworkError,
                message = errorMessage
            )
        }.`when`(getNewsUseCase).invoke(testQuery, testDate, testSortBy)

        // When
        viewModel.getNews(testQuery, testDate, testSortBy)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.newsState.first()
        assertTrue(state is NewsUiState.Error)
        assertEquals(errorMessage, (state as NewsUiState.Error).message)
    }

    @Test
    fun `getNewsByUrl should return correct news item`() = runTest {
        // Given
        doAnswer { Result.Success(mockNews) }.`when`(getNewsUseCase).invoke(testQuery, testDate, testSortBy)

        // When
        viewModel.getNews(testQuery, testDate, testSortBy)
        testDispatcher.scheduler.advanceUntilIdle()
        val newsId = mockNews.last().hashCode()
        val result = viewModel.getNewsByUrl(newsId)

        // Then
        assertEquals(mockNews.last(), result)
    }

    @Test
    fun `getNewsByUrl should return null for non-existent news id`() = runTest {
        // Given
        doAnswer { Result.Success(mockNews) }.`when`(getNewsUseCase).invoke(testQuery, testDate, testSortBy)

        // When
        viewModel.getNews(testQuery, testDate, testSortBy)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.getNewsByUrl(-1) // Non-existent ID

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `getNews shoud show error state if not success`() = runTest {
        runBlocking {
            whenever (getNewsUseCase.invoke(testQuery, testDate, testSortBy))
                .thenReturn (Result.Error(
                    errorType = ErrorType.UnknownError,
                    message = "Something went wrong"
                ))
        }

        viewModel.getNews(testQuery, testDate, testSortBy)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.newsState.first()
        assert(state is NewsUiState.Error)
        assertEquals("Something went wrong", (state as NewsUiState.Error).message)
    }

    @Test
    fun `should emit Loading and then Error when result fails`() = runTest {

        whenever(getNewsUseCase("Messi", "2025-14-09", "publishedAt"))
            .thenReturn(Result.Error(ErrorType.UnknownError, "Unkown error"))

        // When: collect emissions
        val emittedStates = mutableListOf<NewsUiState>()
        val job = launch {
            val viewModel = NewsViewModel(getNewsUseCase)
            viewModel.newsState.toList(emittedStates)
        }

        // Give time for emissions
        advanceUntilIdle()

        // Then: check states
        assertEquals(NewsUiState.Loading, emittedStates[0])
        assertEquals(NewsUiState.Error("Unkown error"), emittedStates[1])

        job.cancel()
    }
} 