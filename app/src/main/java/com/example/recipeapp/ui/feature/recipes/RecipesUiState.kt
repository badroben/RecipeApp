package com.example.recipeapp.ui.feature.recipes

import com.example.recipeapp.data.local.entity.RecipeEntity

data class RecipesUiState(
    val recipes: List<RecipeEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)