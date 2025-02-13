package com.appsolutely.newsapp.data.mapper

import com.appsolutely.newsapp.data.model.NewsDto
import com.appsolutely.newsapp.data.model.SourceDto
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.domain.model.Source


fun NewsDto.toDomain(): News {
    return News(
        source = this.source?.toDomain(),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        imageUrl = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}

fun SourceDto.toDomain(): Source {
    return Source(
        id = this.id,
        name = this.name
    )
}
