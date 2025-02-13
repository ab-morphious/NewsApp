package com.appsolutely.newsapp.domain.usecase

import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.domain.repository.NewsRepository
import javax.inject.Inject
import com.appsolutely.newsapp.common.Result

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String, from: String, sortBy: String): Result<List<News>> {
        return newsRepository.getNews(query, from, sortBy)
    }
}