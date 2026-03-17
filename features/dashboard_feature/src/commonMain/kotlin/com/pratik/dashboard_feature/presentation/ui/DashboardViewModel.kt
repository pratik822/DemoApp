package com.pratik.dashboard_feature.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DashboardEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleIntent(DashboardIntent.LoadCategories)
    }

    fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadCategories -> loadCategories()
            is DashboardIntent.OnCategoryClick -> navigateToHomeListing(intent.category)
            is DashboardIntent.OnMapClick -> navigateToMap()
        }
    }

    private fun loadCategories() {
        _state.update { it.copy(isLoading = true) }
        val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
        _state.update { it.copy(categories = categories, isLoading = false) }
    }

    private fun navigateToHomeListing(category: String) {
        viewModelScope.launch {
            _effect.emit(DashboardEffect.NavigateToHomeListing(category))
        }
    }

    private fun navigateToMap() {
        viewModelScope.launch {
            _effect.emit(DashboardEffect.NavigateToMap)
        }
    }
}
