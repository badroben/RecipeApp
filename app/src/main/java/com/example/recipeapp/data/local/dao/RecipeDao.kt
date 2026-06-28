package com.example.recipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * from recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * from recipes WHERE isFavourite == 1")
    fun getFavouriteRecipes(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET isFavourite = :isFavourite WHERE id = :recipeId")
    suspend fun updateFavourite(recipeId: Int, isFavourite: Boolean)
}