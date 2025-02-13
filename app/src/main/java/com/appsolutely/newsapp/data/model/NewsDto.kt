package com.appsolutely.newsapp.data.model


data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsDto>
)

data class NewsDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceDto(
    val id: String?,
    val name: String?
)
