package com.example.recipeapp.ui.feature.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    val uiState =
        combine(
            repository.getFavouriteRecipes(),
            _searchQuery
        ) { favourites, query ->
            val filteredRecipes =
                if (query.isBlank()) {
                    favourites
                } else {
                    favourites.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                }
            FavouritesUiState(
                favourites = filteredRecipes,
                searchQuery = query
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FavouritesUiState()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    fun toggleFavourite(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.toggleFavourite(recipe.id, !recipe.isFavourite)
        }
    }
}