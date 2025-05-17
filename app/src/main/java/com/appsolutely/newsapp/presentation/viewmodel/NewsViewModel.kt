package com.appsolutely.newsapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _newsState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val newsState: StateFlow<NewsUiState> = _newsState

    private val _newsList = mutableStateOf<List<News>>(emptyList())

    init {
        getNews("Messi", "2025-14-09", "publishedAt")
    }

    fun getNews(query: String, from: String, sortBy: String) {
        viewModelScope.launch {
            _newsState.value = NewsUiState.Loading

            val result = getNewsUseCase(query, from, sortBy)

            if(result != null) {
                _newsState.value = if (result.isSuccess()) {
                    _newsList.value = result.getOrDefault(emptyList())
                    NewsUiState.Success(result.getOrDefault(emptyList()))
                } else {
                    NewsUiState.Error(result.exceptionOrNull()?.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getNewsByUrl(newsId: Int): News? {
        return _newsList.value.find { it.hashCode() == newsId }
    }
}

sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(val news: List<News>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}
