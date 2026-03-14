package com.pratik.home_listing_feature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pratik.demoapp.core.utils.NewsList
import com.pratik.demoapp.core.utils.PlatformNetworkImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeListingScreen(
    category: String,
    onItemClick: (post: NewsList) -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val viewModel: HomeListingScreenViewModel = koinViewModel()
    val state by viewModel.allPostState.collectAsState()

    LaunchedEffect(category) {
        viewModel.processIntent(PostIntent.LoadPost(category))
    }

    Scaffold(
        topBar = {
            CommonAppBar(
                title = "News Listing",
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(Icons.Filled.Favorite, "Favorites", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        NewsContent(
            state = state,
            onItemClick = onItemClick,
            onFavoriteToggle = { post -> viewModel.processIntent(PostIntent.ToggleFavorite(post)) },
            modifier = Modifier.padding(padding)
        )
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    title: String,
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, "Back", tint = Color.White)
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun NewsContent(
    state: PostState,
    onItemClick: (NewsList) -> Unit,
    onFavoriteToggle: (NewsList) -> Unit,
    modifier: Modifier = Modifier,
    emptyMessage: String = "No articles found"
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is PostState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is PostState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
            is PostState.Success -> {
                if (state.post.isEmpty()) {
                    Text(emptyMessage, modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.post, key = { it.url }) { post ->
                            PostItem(
                                post = post,
                                onClick = { onItemClick(post) },
                                onFavoriteToggle = { onFavoriteToggle(post) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(post: NewsList, onClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (post.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (post.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            PlatformNetworkImage(
                url = post.urlToImage,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
