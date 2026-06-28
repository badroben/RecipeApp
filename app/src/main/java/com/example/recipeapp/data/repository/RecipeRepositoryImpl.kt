package com.example.recipeapp.data.repository

import com.example.recipeapp.data.local.dao.RecipeDao
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.repository.RecipeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class RecipeRepositoryImpl @Inject constructor(
    private val dao: RecipeDao
) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<RecipeEntity>> = dao.getAllRecipes()
    override fun getFavouriteRecipes(): Flow<List<RecipeEntity>> = dao.getFavouriteRecipes()
    override suspend fun toggleFavourite(recipeId: Int, isFavourite: Boolean) =
        dao.updateFavourite(recipeId, isFavourite)
}