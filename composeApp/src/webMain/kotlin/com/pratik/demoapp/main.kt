package com.pratik.demoapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.pratik.demoapp.di.appModules
import kotlinx.browser.document
import org.koin.core.context.startKoin


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(appModules)
    }

    ComposeViewport(
        document.body!!
    ) {
        App()
    }
}
