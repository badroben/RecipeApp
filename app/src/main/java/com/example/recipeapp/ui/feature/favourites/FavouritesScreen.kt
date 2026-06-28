package com.example.recipeapp.ui.feature.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.ui.feature.recipes.RecipeCard
import com.example.recipeapp.ui.feature.recipes.SearchableTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onHomeButtonClicked: () -> Unit,
    onRecipeClick: (RecipeEntity) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    var isSearching by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            SearchableTopBar(
                title = "Favourites",
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                isSearching = isSearching,
                onSearchToggle = {
                    isSearching = !isSearching
                    if (!isSearching) viewModel.onSearchQueryChange("")
                },
                onBackClicked = onBackClicked
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    IconButton (
                        onClick = onHomeButtonClicked
                    ){
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                }
            }
        },
    ) { innerPadding ->

        LazyColumn(contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ){
            items(uiState.favourites.size) { index ->
                val recipe = uiState.favourites[index]
                RecipeCard(
                    recipe = recipe,
                    onRecipeClick = onRecipeClick
                )
            }
        }
    }
}