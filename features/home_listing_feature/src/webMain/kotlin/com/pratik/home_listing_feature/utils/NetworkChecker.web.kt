package com.pratik.home_listing_feature.utils

import kotlinx.browser.window

actual class NetworkChecker actual constructor() {
    actual fun isNetworkAvailable(): Boolean {
        return window.navigator.onLine
    }
}