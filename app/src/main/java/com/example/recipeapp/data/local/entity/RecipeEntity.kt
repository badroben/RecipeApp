package com.example.recipeapp.data.local.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val isFavourite: Boolean = false,
    @DrawableRes val image: Int
)