package com.pratik.demoapp.core.utils

import coil3.compose.AsyncImage

@androidx.compose.runtime.Composable
actual fun PlatformNetworkImage(
    url: String,
    modifier: androidx.compose.ui.Modifier,
    contentDescription: String?,
    contentScale: androidx.compose.ui.layout.ContentScale
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}