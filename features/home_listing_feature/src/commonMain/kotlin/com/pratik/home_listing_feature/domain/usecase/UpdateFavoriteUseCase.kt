package com.pratik.home_listing_feature.domain.usecase

import com.pratik.home_listing_feature.domain.repository.PostRepository

class UpdateFavoriteUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(url: String, isFavorite: Boolean) {
        repository.updateFavorite(url, isFavorite)
    }
}
