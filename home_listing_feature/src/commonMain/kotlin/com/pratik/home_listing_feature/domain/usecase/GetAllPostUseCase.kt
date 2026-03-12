package com.pratik.home_listing_feature.domain.usecase

import com.pratik.demoapp.core.utils.NewsList
import com.pratik.home_listing_feature.domain.repository.PostRepository


class GetAllPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(category:String): List<NewsList> {
        return postRepository.getAllPost(category)
    }
}
