package com.pratik.home_listing_feature.ui

import com.pratik.demoapp.core.utils.NewsList

sealed class PostIntent {
    class loadPost(val category: String) : PostIntent()
    class ToggleFavorite(val post: NewsList) : PostIntent()
    object LoadFavorites : PostIntent()
}
