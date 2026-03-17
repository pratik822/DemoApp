package com.pratik.home_listing_feature.utils

import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import kotlin.js.ExperimentalWasmJsInterop

actual class BackgroundWorker : KoinComponent {
    private val getAllPostUseCase: GetAllPostUseCase by inject()
    private val scope = CoroutineScope(Dispatchers.Default)

    @OptIn(ExperimentalWasmJsInterop::class)
    actual fun startTask() {
        window.setInterval({
            scope.launch {
                try {
                    val category = "general"
                    getAllPostUseCase(category)
                } catch (e: Exception) {
                    println("Error syncing news: ${e.message}")
                }
            }
            null
        }, 5000)
    }
}
