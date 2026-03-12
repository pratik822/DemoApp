package com.pratik.demoapp.core.navigation

import androidx.compose.runtime.Composable
import com.pratik.demoapp.core.utils.NewsList

object FeatureRegistry {
    var DashBoardScreen: @Composable ((String) -> Unit) -> Unit = { }
    var HomeListingScreen: @Composable (String, (NewsList) -> Unit, () -> Unit) -> Unit = { _, _, _ -> }
    var HomeDetailScreen: @Composable (NewsList, () -> Unit) -> Unit = { _, _ -> }
}
