package com.pratik.home_listing_feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import com.pratik.home_listing_feature.domain.usecase.GetSaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.SaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.UpdateFavoriteUseCase
import com.pratik.home_listing_feature.domain.usecase.GetFavoriteNewsUseCase
import com.pratik.home_listing_feature.utils.NetworkChecker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeListingScreenViewModel(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSaveNewsUseCase: GetSaveNewsUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val networkChecker: NetworkChecker,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _allPostState = MutableStateFlow<PostState>(
        PostState.Loading)
    val allPostState = _allPostState.asStateFlow()

    fun processIntent(postIntent: PostIntent) {
        when (postIntent) {
            is PostIntent.loadPost -> getAllPost(postIntent.category)
            is PostIntent.ToggleFavorite -> toggleFavorite(postIntent.post)
            is PostIntent.LoadFavorites -> getFavorites()
        }
    }

    private fun getAllPost(category: String) {
        viewModelScope.launch(dispatcher) {
            if(networkChecker.isNetworkAvailable()){
                try {
                    val post = getAllPostUseCase.invoke(category)
                        _allPostState.update { PostState.Success(post) }
                        saveNewsUseCase(category,post)


                } catch (e: Exception) {
                    _allPostState.update { PostState.Error(e.message ?: "Unknown error") }
                }
            }else{
                _allPostState.update { PostState.Success(getSaveNewsUseCase(category)) }
            }

        }
    }

    private fun toggleFavorite(post: com.pratik.demoapp.core.utils.NewsList) {
        viewModelScope.launch(dispatcher) {
            val newFavoriteState = !post.isFavorite
            updateFavoriteUseCase(post.url, newFavoriteState)
            
            _allPostState.update { currentState ->
                if (currentState is PostState.Success) {
                    val updatedList = currentState.post.map {
                        if (it.url == post.url) it.copy(isFavorite = newFavoriteState) else it
                    }
                    PostState.Success(updatedList)
                } else {
                    currentState
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch(dispatcher) {
            _allPostState.update { PostState.Loading }
            try {
                val favorites = getFavoriteNewsUseCase()
                _allPostState.update { PostState.Success(favorites) }
            } catch (e: Exception) {
                _allPostState.update { PostState.Error(e.message ?: "Unknown error") }
            }
        }
    }
}
