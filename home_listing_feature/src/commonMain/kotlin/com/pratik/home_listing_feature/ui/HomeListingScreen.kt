package com.pratik.home_listing_feature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pratik.demoapp.core.utils.NewsList
import com.pratik.demoapp.core.utils.PlatformNetworkImage

import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeListingScreen(
    category:String, onItemClick: (post: NewsList) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: HomeListingScreenViewModel = koinViewModel()
    val state by viewModel.allPostState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.processIntent(PostIntent.loadPost(category))
    }
    Scaffold(topBar = { AppBar(onBackClick) }) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            val currentState = state
            when (currentState) {
                is PostState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is PostState.Success -> {
                    _root_ide_package_.com.pratik.home_listing_feature.ui.NewsListScreen(
                        news = currentState.post,
                        onItemClick = onItemClick
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
fun AppBar(onBackClick:()->Unit){
    TopAppBar(title = {Text("News Listing")},
        navigationIcon = {
            IconButton(
                onClick = {onBackClick()}
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue,
            titleContentColor = Color.White
        ))
}

@Composable
fun NewsListScreen(
    news: List<NewsList>,
    onItemClick: (NewsList) -> Unit
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
                onClick = { onItemClick(post) })
        }
    }
}

@Composable
fun PostItem(post: NewsList, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = post.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            PlatformNetworkImage(url = post.urlToImage, modifier = Modifier.fillMaxWidth().height(200.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
