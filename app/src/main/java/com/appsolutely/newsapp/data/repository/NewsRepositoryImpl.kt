package com.appsolutely.newsapp.data.repository

import com.appsolutely.newsapp.data.datasource.remote.NewsRemoteDataSource
import com.appsolutely.newsapp.data.mapper.toDomain
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.domain.repository.NewsRepository
import javax.inject.Inject
import com.appsolutely.newsapp.common.Result

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {
    override suspend fun getNews(query: String, from: String, sortBy: String): Result<List<News>> {
        return when (val result = newsRemoteDataSource.fetchNews(query, from, sortBy)) {
            is Result.Success -> Result.Success(
                result.data.map { it.toDomain() }
            )

            is Result.Error -> result
        }
    }
}