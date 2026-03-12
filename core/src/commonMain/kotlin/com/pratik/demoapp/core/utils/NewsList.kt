package com.pratik.demoapp.core.utils

import kotlinx.serialization.Serializable

@Serializable
data class NewsList(val name: String,val author: String,val title: String,val description: String,val url: String,val urlToImage: String,val publishedAt: String)
