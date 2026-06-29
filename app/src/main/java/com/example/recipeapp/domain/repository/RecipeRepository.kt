package com.example.recipeapp.domain.repository

import com.example.recipeapp.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
interface RecipeRepository {
    fun getAllRecipes(): Flow<List<RecipeEntity>>
    fun getFavouriteRecipes(): Flow<List<RecipeEntity>>
    suspend fun addRecipe(recipe: RecipeEntity)
    suspend fun deleteRecipe(recipe: RecipeEntity)
    suspend fun updateRecipe(recipe: RecipeEntity)
    suspend fun toggleFavourite(recipeId: Int, isFavourite: Boolean)
}