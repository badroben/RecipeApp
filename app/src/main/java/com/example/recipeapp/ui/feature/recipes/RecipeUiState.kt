package com.example.recipeapp.ui.feature.recipes

import com.example.recipeapp.data.local.entity.RecipeEntity

data class RecipeUiState(
    val favourites: List<RecipeEntity> = emptyList()
)