package com.pratik.home_listing_feature.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class NetworkChecker actual constructor() : KoinComponent {
    private val context: Context by inject()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    actual fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
