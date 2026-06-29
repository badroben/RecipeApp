package com.example.recipeapp.ui.feature.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    val uiState =
        combine(
            repository.getAllRecipes(),
            _searchQuery
        ) { recipes, query ->
            val filteredRecipes =
                if (query.isBlank()) {
                    recipes
                } else {
                    recipes.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                }
            RecipesUiState(
                recipes = filteredRecipes,
                searchQuery = query
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RecipesUiState()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    fun toggleFavourite(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.toggleFavourite(recipe.id, !recipe.isFavourite)
        }
    }

    fun addRecipe(title: String, description: String, ingredients: List<String>, image: Int){
        viewModelScope.launch {
            repository.addRecipe(
                RecipeEntity(
                    title = title,
                    description = description,
                    ingredients = ingredients,
                    image = image,
                    isFavourite = false
                )
            )
        }
    }

    fun deleteRecipe(recipe: RecipeEntity){
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: RecipeEntity){
        viewModelScope.launch {
            repository.updateRecipe(recipe)
        }
    }
}