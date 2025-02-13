package com.appsolutely.newsapp.data.api

import com.appsolutely.newsapp.data.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsApiResponse>
}
