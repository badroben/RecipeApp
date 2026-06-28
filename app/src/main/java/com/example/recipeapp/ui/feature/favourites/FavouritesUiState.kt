package com.example.recipeapp.ui.feature.favourites

import com.example.recipeapp.data.local.entity.RecipeEntity

data class FavouritesUiState(
    val favourites: List<RecipeEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)