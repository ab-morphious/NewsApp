package com.appsolutely.newsapp.domain.repository

import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.common.Result


interface NewsRepository {
    suspend fun getNews(query: String, from: String, sortBy: String): Result<List<News>>
}
