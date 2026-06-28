package com.example.recipeapp.ui.feature.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {
    val recipes: StateFlow<List<RecipeEntity>> = repository.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val uiState: StateFlow<RecipeUiState> = repository.getFavouriteRecipes()
        .map { favourites -> RecipeUiState(favourites = favourites) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecipeUiState())

    fun toggleFavourite(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.toggleFavourite(recipe.id, !recipe.isFavourite)
        }
    }
}