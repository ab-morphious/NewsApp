package com.appsolutely.newsapp.domain.model

data class News(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String?
)

