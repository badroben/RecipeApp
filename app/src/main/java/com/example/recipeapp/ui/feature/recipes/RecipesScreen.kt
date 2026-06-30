package com.example.recipeapp.ui.feature.recipes

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.ui.theme.Cream
import com.example.recipeapp.ui.theme.DeepBrown
import com.example.recipeapp.ui.theme.StarGold
import com.example.recipeapp.ui.theme.Terracotta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onRecipeClick: (RecipeEntity) -> Unit,
    onBackClicked: () -> Unit,
    onAddRecipeClicked: () -> Unit,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Cream,
        topBar = {
            SearchableTopBar(
                title = "Recipes",
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecipeClicked,
                containerColor = Terracotta,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add recipe")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
                .padding(horizontal = 16.dp)
        ) {
            items(uiState.recipes.size) { index ->
                RecipeCard(
                    recipe = uiState.recipes[index],
                    onRecipeClick = onRecipeClick
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: RecipeEntity,
    onRecipeClick: (RecipeEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onRecipeClick(recipe) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        recipe.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DeepBrown
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        recipe.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DeepBrown.copy(alpha = 0.7f),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(id = recipe.image),
                        contentDescription = recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // ----- Meta row: category · time · rating -----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetaChip(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    text = recipe.category.displayName
                )
                MetaChip(
                    icon = Icons.Default.AccessTime,
                    text = "${recipe.cookingTime} min"
                )
                MetaChip(
                    icon = Icons.Default.Star,
                    text = "${recipe.rating}",
                    iconTint = StarGold
                )
            }
        }
    }
}

@Composable
private fun MetaChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    iconTint: Color = Terracotta
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Terracotta.copy(alpha = 0.10f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text(
                text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = DeepBrown
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(
    title: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearching: Boolean,
    onSearchToggle: () -> Unit,
    onBackClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search recipes...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
                        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f)
                    )
                )
            } else {
                Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
            }
        },
        actions = {
            IconButton(onClick = onSearchToggle) {
                Icon(
                    imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if (isSearching) "Close search" else "Search"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Terracotta,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun StarRatingInput(
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    Row {
        for (star in 1..5) {
            IconButton(onClick = { onRatingChange(star) }) {
                Icon(
                    imageVector = if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Rate $star stars",
                    tint = if (star <= rating) StarGold else DeepBrown.copy(alpha = 0.3f)
                )
            }
        }
    }
}