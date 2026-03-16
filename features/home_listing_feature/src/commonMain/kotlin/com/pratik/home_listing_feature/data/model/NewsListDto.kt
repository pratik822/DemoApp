package com.pratik.home_listing_feature.data.model

import com.pratik.demoapp.core.utils.NewsList
import kotlinx.serialization.Serializable

@Serializable
data class NewsListDto(
    val articles: List<Article> = emptyList(),
    val status: String? = null,
    val totalResults: Int? = null
)

fun Article.toNewsList(): NewsList {
    return NewsList(
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        publishedAt = publishedAt ?: "",
        author = author ?: "",
        name = source?.name ?: ""
    )
}
