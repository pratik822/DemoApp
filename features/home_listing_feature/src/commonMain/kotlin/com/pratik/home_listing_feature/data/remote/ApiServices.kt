package com.pratik.home_listing_feature.data.remote

import com.pratik.home_listing_feature.data.model.NewsListDto


interface ApiServices {
    suspend fun getAllPost(category: String): NewsListDto
}
