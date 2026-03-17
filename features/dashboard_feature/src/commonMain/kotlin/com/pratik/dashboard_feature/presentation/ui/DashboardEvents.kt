package com.pratik.dashboard_feature.presentation.ui

data class DashboardState(
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class DashboardIntent {
    object LoadCategories : DashboardIntent()
    data class OnCategoryClick(val category: String) : DashboardIntent()
    object OnMapClick : DashboardIntent()
}

sealed class DashboardEffect {
    data class NavigateToHomeListing(val category: String) : DashboardEffect()
    object NavigateToMap : DashboardEffect()
}
