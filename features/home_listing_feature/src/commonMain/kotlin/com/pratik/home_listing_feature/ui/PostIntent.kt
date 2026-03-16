package com.pratik.home_listing_feature.ui

import com.pratik.demoapp.core.utils.NewsList

sealed class PostIntent {
    data class LoadPost(val category: String) : PostIntent()
    data class ToggleFavorite(val post: NewsList) : PostIntent()
    object LoadFavorites : PostIntent()
}
