package com.example.recipeapp.ui.feature.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.ui.feature.recipes.RecipeCard
import com.example.recipeapp.ui.feature.recipes.SearchableTopBar
import com.example.recipeapp.ui.theme.Cream
import com.example.recipeapp.ui.theme.DeepBrown
import com.example.recipeapp.ui.theme.Terracotta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onHomeButtonClicked: () -> Unit,
    onRecipeClick: (RecipeEntity) -> Unit,
    onBackClicked: () -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Cream,
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
            BottomAppBar(
                containerColor = Terracotta,
                contentColor = Color.White,
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        onClick = onHomeButtonClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White.copy(alpha = 0.18f),
                        contentColor = Color.White
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(24.dp))
                            Spacer(Modifier.size(8.dp))
                            Text("Home", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (uiState.favourites.isEmpty()) {
            // ----- Empty state: an invitation, not a blank screen -----
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Cream)
                    .padding(innerPadding)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Terracotta.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.size(16.dp))
                    Text(
                        "No favourites yet",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DeepBrown
                    )
                    Spacer(Modifier.size(6.dp))
                    Text(
                        "Tap the heart on any recipe to save it here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DeepBrown.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Cream)
                    .padding(horizontal = 16.dp)
            ) {
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
}