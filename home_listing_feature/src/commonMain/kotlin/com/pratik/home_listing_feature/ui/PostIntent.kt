package com.pratik.home_listing_feature.ui

sealed class PostIntent {
    class loadPost(val category:String) : com.pratik.home_listing_feature.ui.PostIntent()
}
