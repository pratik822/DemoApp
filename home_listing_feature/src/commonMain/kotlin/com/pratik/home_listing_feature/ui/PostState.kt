package com.pratik.home_listing_feature.ui

import com.pratik.demoapp.core.utils.NewsList


sealed class PostState {
    object Loading : PostState()
    data class Success(val post: List<NewsList>) : PostState()
    data class Error(val message: String) : PostState()
}
