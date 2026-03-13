package com.pratik.dashboard_feature.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun DashBoardScreen(onItemClick: (String) -> Unit, onMapClick: () -> Unit) {
    val listItem = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

    Scaffold(topBar = { AppBars(onMapClick) }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(listItem) { item ->
                    GridItem(item, onItemClick = onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBars(onMapClick: () -> Unit) {
    TopAppBar(
        title = { Text("Category") },
        actions = {
            IconButton(onClick = onMapClick) {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = "Map",
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
fun GridItem(item: String, onItemClick: (String) -> Unit) {
    val randomColor = remember {
        Color(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            1f
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(randomColor)
            .padding(10.dp)
            .clickable { onItemClick(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(item, color = Color.White, textAlign = TextAlign.Center)
    }

}
