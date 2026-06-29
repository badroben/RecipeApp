package com.example.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipeapp.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * from recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * from recipes WHERE isFavourite == 1")
    fun getFavouriteRecipes(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET isFavourite = :isFavourite WHERE id = :recipeId")
    suspend fun updateFavourite(recipeId: Int, isFavourite: Boolean)
}