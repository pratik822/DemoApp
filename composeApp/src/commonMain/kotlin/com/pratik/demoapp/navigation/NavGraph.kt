package com.pratik.demoapp.navigation

import androidx.compose.runtime.Composable
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

    NavDisplay(
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
