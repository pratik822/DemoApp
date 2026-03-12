package com.pratik.home_listing_feature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsList(val name: String,val author: String,val title: String,val description: String,val url: String,val urlToImage: String,val publishedAt: String)
