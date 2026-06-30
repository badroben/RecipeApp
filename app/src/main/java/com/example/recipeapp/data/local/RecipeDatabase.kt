package com.example.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.data.local.dao.RecipeDao
import com.example.recipeapp.data.local.entity.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}