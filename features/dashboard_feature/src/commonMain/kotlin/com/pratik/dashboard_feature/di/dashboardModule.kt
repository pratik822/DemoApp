package com.pratik.dashboard_feature.di

import com.pratik.dashboard_feature.presentation.ui.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardModule = module {
    viewModelOf(::DashboardViewModel)
}
