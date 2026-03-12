package com.pratik.home_listing_feature.di

import com.pratik.home_listing_feature.data.remote.ApiServices
import com.pratik.home_listing_feature.data.remote.ApiServicesImpl
import com.pratik.home_listing_feature.data.repository.PostRepositoryImpl
import com.pratik.home_listing_feature.domain.repository.PostRepository
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import com.pratik.home_listing_feature.domain.usecase.SaveNewsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.pratik.home_listing_feature.ui.HomeListingScreenViewModel

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
    single<ApiServices> {
        ApiServicesImpl(get())
    }
}

val repositoryModule = module {
    single<PostRepository> {
        PostRepositoryImpl(get(), get())
    }
}


val useCaseModule = module {
    factory {
        GetAllPostUseCase(
            get()
        )
    }
    factory {
        SaveNewsUseCase(
            get()
        )
    }
}

val viewModels = module {
    factory {
        HomeListingScreenViewModel(
            get(),
            get(),
            get(),
            get(),
            networkChecker = get(),
            getFavoriteNewsUseCase = get(),
            dispatcher = Dispatchers.Main
        )
    }
}

fun initKoin(){
    startKoin {
        modules(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModels
        )

    }
}
