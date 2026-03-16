package com.pratik.home_listing_feature.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: com.pratik.home_listing_feature.data.model.Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)
