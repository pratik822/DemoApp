package com.pratik.demoapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.pratik.dashboard_feature.presentation.ui.DashBoardScreen
import com.pratik.news_detail_feature.presentation.ui.HomeDetailScreenComposable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import com.pratik.home_listing_feature.ui.HomeListingScreen
import com.pratik.home_listing_feature.ui.FavoriteListingScreen
import com.pratik.demoapp.presentation.ui.MapScreen

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Home::class, Home.serializer())
            subclass(Details::class, Details.serializer())
            subclass(Dashboard::class, Dashboard.serializer())
            subclass(Favorites::class, Favorites.serializer())
            subclass(MapRoute::class, MapRoute.serializer())
        }
    }
}

@Composable
fun NavGraph() {
    val backStack = rememberNavBackStack(config, Dashboard())
    val currentKey = backStack.lastOrNull()

    Scaffold(
        bottomBar = {
            if (currentKey is Dashboard || currentKey is Favorites || currentKey is MapRoute) {
                AppBottomNavigation(
                    currentKey = currentKey,
                    onSelect = { route ->
                        if (currentKey::class != route::class) {
                            backStack.remove(currentKey)
                            backStack.add(route)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() }
        ) { key ->
            when (key) {
                is Dashboard -> NavEntry(key) {
                    DashBoardScreen(
                        onItemClick = { category ->
                            backStack.add(Home(item = category))
                        },
                        onMapClick = {
                            backStack.add(MapRoute())
                        }
                    )
                }
                is Home -> NavEntry(key) {
                    HomeListingScreen(
                        category = key.item,
                        onItemClick = { selectedPost ->
                            backStack.add(Details(post = selectedPost))
                        },
                        onBackClick = { backStack.remove(key) },
                        onFavoriteClick = {
                            backStack.add(Favorites())
                        }
                    )
                }
                is Favorites -> NavEntry(key) {
                    FavoriteListingScreen(
                        onItemClick = { selectedPost ->
                            backStack.add(Details(post = selectedPost))
                        },
                        onBackClick = { backStack.remove(key) }
                    )
                }
                is MapRoute -> NavEntry(key) {
                    MapScreen()
                }
                is Details -> NavEntry(key) {
                    HomeDetailScreenComposable(
                        details = key.post,
                        onBackPress = { backStack.remove(key) }
                    )
                }
                else -> NavEntry(key) { }
            }
        }
    }
}

@Composable
fun AppBottomNavigation(
    currentKey: NavKey,
    onSelect: (NavKey) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentKey is Dashboard,
            onClick = { onSelect(Dashboard()) },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            selected = currentKey is Favorites,
            onClick = { onSelect(Favorites()) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") }
        )
        NavigationBarItem(
            selected = currentKey is MapRoute,
            onClick = { onSelect(MapRoute()) },
            icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") }
        )
    }
}
