package com.pratik.home_listing_feature.domain.usecase

import com.pratik.demoapp.core.utils.NewsList
import com.pratik.home_listing_feature.domain.repository.PostRepository

class SaveNewsUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(category:String,newsList: List<NewsList>) {
        postRepository.saveNews(category,newsList)
    }
}
