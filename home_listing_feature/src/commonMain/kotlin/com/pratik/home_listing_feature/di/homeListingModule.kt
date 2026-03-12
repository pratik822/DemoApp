// home_listing_feature/src/commonMain/kotlin/com/pratik/home_listing_feature/di/homeListingModule.kt

package com.pratik.home_listing_feature.di

import com.pratik.home_listing_feature.data.remote.ApiServices
import com.pratik.home_listing_feature.data.remote.ApiServicesImpl
import com.pratik.home_listing_feature.data.repository.PostRepositoryImpl
import com.pratik.home_listing_feature.domain.repository.PostRepository
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import com.pratik.home_listing_feature.domain.usecase.GetSaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.SaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.UpdateFavoriteUseCase
import com.pratik.home_listing_feature.domain.usecase.GetFavoriteNewsUseCase
import com.pratik.home_listing_feature.ui.HomeListingScreenViewModel
import com.pratik.home_listing_feature.utils.NetworkChecker
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val homeListingModule = module {
    single<ApiServices> { ApiServicesImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    factory { GetAllPostUseCase(get()) }
    factory { GetSaveNewsUseCase(get()) }
    factory { SaveNewsUseCase(get()) }
    factory { UpdateFavoriteUseCase(get()) }
    factory { GetFavoriteNewsUseCase(get()) }
    factory { NetworkChecker() }
    factory { HomeListingScreenViewModel(get(), get(), get(), get(), get(), get(), Dispatchers.Main) }
}
