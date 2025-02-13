package com.appsolutely.newsapp.data.datasource.remote

import com.appsolutely.newsapp.BuildConfig
import com.appsolutely.newsapp.data.api.NewsApiService
import com.appsolutely.newsapp.data.model.NewsDto
import retrofit2.HttpException
import javax.inject.Inject
import com.appsolutely.newsapp.common.Result
import com.appsolutely.newsapp.common.safeApiCall

class NewsRemoteDataSource @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchNews(query: String, from: String, sortBy: String): Result<List<NewsDto>> {
        return safeApiCall {
            val response = apiService.getNews(query, from, sortBy, BuildConfig.NEWS_API_KEY)
            if (response.isSuccessful) {
                response.body()?.articles ?: emptyList()
            } else {
                throw HttpException(response)
            }
        }
    }
}
