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

    LaunchedEffect(Unit) {
        viewModel.processIntent(PostIntent.loadPost(category))
    }
    Scaffold(topBar = { AppBar(onBackClick, onFavoriteClick) }) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            val currentState = state
            when (currentState) {
                is PostState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is PostState.Success -> {
                    NewsListScreen(
                        news = currentState.post,
                        onItemClick = onItemClick,
                        onFavoriteToggle = { post ->
                            viewModel.processIntent(PostIntent.ToggleFavorite(post))
                        }
                    )
                }

                is PostState.Error -> {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onBackClick: () -> Unit, onFavoriteClick: () -> Unit) {
    TopAppBar(
        title = { Text("News Listing") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorites",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
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

    Scaffold(topBar = {
        FavoriteAppBar(onBackClick)
    }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            val currentState = state
            when (currentState) {
                is PostState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is PostState.Success -> {
                    if (currentState.post.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No favorites yet")
                        }
                    } else {
                        NewsListScreen(
                            news = currentState.post,
                            onItemClick = onItemClick,
                            onFavoriteToggle = { post ->
                                viewModel.processIntent(PostIntent.ToggleFavorite(post))
                            }
                        )
                    }
                }

                is PostState.Error -> {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text("Favorites") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun NewsListScreen(
    news: List<NewsList>,
    onItemClick: (NewsList) -> Unit,
    onFavoriteToggle: (NewsList) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news) { post ->
            PostItem(
                post = post,
                onClick = { onItemClick(post) },
                onFavoriteToggle = { onFavoriteToggle(post) })
        }
    }
}

@Composable
fun PostItem(post: NewsList, onClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            Spacer(modifier = Modifier.height(8.dp))
            PlatformNetworkImage(
                url = post.urlToImage,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
