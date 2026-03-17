package com.pratik.home_listing_feature.domain.usecase

import com.pratik.demoapp.core.utils.NewsList
import com.pratik.home_listing_feature.domain.repository.PostRepository

class GetFavoriteNewsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(): List<NewsList> {
        return repository.getAllFavorites()
    }
}
