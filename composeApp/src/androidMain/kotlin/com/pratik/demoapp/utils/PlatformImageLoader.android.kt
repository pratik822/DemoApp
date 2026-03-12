package com.pratik.demoapp.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter

@Composable
actual fun PlatformNetworkImage(
    url: String,
    modifier: Modifier,
    contentDescription: String?,
    contentScale: ContentScale
) {
    Image(painter = rememberAsyncImagePainter(url),modifier = modifier, contentDescription = null,contentScale = ContentScale.Crop)
}