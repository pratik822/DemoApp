package com.pratik.demoapp.di

import com.pratik.demoapp.database.DatabaseDriverFactory
import com.pratik.demoapp.db.AppDatabase
import com.pratik.home_listing_feature.di.homeListingModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val databaseModule = module {
    single { 
        val driver = get<DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
        }
    }
}

val appModules = listOf(
    platformModule,
    networkModule,
    databaseModule,
    homeListingModule
)

fun initKoin() {
    startKoin {
        modules(appModules)
    }
}
