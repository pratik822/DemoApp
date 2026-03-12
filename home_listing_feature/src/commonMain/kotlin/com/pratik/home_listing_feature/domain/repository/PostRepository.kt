package com.pratik.home_listing_feature.domain.repository

import com.pratik.demoapp.core.utils.NewsList
import com.pratik.demoapp.db.NewsEntity


interface PostRepository {
    suspend fun getAllPost(category: String): List<NewsList>
    suspend fun saveNews(category:String,newsList: List<NewsList>)

    suspend fun getSavedNews(category: String): List<NewsList>
}
