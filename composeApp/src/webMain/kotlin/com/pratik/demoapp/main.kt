package com.pratik.demoapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

import com.pratik.demoapp.di.initKoin


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Koin init if still needed for features
    initKoin()
    
    // Manual DI init
//    val driverFactory = DatabaseDriverFactory()
//    val container = AppContainer(driverFactory)

    ComposeViewport {
        App()
    }
}
