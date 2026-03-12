package com.pratik.demoapp.utils

import kotlinx.browser.window

actual class NetworkChecker actual constructor() {
    actual fun isNetworkAvailable(): Boolean {
        return window.navigator.onLine
    }
}