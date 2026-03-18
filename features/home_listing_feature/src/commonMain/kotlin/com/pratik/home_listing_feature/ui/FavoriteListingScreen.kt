package com.pratik.home_listing_feature.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pratik.demoapp.core.utils.NewsList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteListingScreen(
    onItemClick: (post: NewsList) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: HomeListingScreenViewModel = koinViewModel()
    val state by viewModel.allPostState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(PostIntent.LoadFavorites)
    }

    Scaffold(
        topBar = {
            CommonAppBar(
                title = "Favorites",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        NewsContent(
            state = state,
            onItemClick = onItemClick,
            onFavoriteToggle = { post -> viewModel.processIntent(PostIntent.ToggleFavorite(post)) },
            modifier = Modifier.padding(padding),
            emptyMessage = "No favorites yet"
        )
    }
}