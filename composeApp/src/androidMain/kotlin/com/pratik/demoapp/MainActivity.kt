package com.pratik.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pratik.demoapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        // Ensure Koin is started before initializing features
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity.applicationContext)
                modules(appModules)
            }
        }

        // Initialize dynamic features
        initializeFeature("com.pratik.dashboard_feature.DashboardFeature")
        initializeFeature("com.pratik.home_listing_feature.HomeListingFeature")
        initializeFeature("com.pratik.news_detail_feature.NewsDetailFeature")

        setContent {
            App()
        }
    }

    private fun initializeFeature(className: String) {
        try {
            val clazz = Class.forName(className)
            val method = clazz.getMethod("initialize")
            val instance = clazz.getField("INSTANCE").get(null)
            method.invoke(instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
