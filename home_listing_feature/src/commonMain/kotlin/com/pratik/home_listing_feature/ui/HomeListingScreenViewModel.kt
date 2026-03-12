package com.pratik.home_listing_feature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import com.pratik.home_listing_feature.domain.usecase.GetSaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.SaveNewsUseCase
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
    private val networkChecker: NetworkChecker,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _allPostState = MutableStateFlow<PostState>(
        PostState.Loading)
    val allPostState = _allPostState.asStateFlow()

    fun processIntent(postIntent: PostIntent) {
        when (postIntent) {
            is PostIntent.loadPost -> getAllPost(postIntent.category)
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
}
