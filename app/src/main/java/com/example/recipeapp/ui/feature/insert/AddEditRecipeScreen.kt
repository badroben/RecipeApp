package com.example.recipeapp.ui.feature.insert

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.model.Category
import com.example.recipeapp.ui.feature.recipes.StarRatingInput
import com.example.recipeapp.ui.theme.Cream
import com.example.recipeapp.ui.theme.DeepBrown
import com.example.recipeapp.ui.theme.Terracotta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(
    existingRecipe: RecipeEntity?,
    onSave: (title: String, description: String, ingredients: List<String>, cookingTime: Int, category: Category, rating: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    var title by remember { mutableStateOf(existingRecipe?.title ?: "") }
    var description by remember { mutableStateOf(existingRecipe?.description ?: "") }
    var ingredientsText by remember {
        mutableStateOf(existingRecipe?.ingredients?.joinToString(", ") ?: "")
    }
    // Fixed: guard the null case so a new recipe starts blank, not "null"
    var cookingTime by remember {
        mutableStateOf(existingRecipe?.cookingTime?.toString() ?: "")
    }
    var selectedCategory by remember { mutableStateOf(existingRecipe?.category ?: Category.DINNER) }
    var rating by remember { mutableStateOf(existingRecipe?.rating ?: 0) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Terracotta,
        unfocusedBorderColor = DeepBrown.copy(alpha = 0.3f),
        focusedLabelColor = Terracotta,
        cursorColor = Terracotta,
        focusedLeadingIconColor = Terracotta
    )

    Scaffold(
        containerColor = Cream,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (existingRecipe == null) "Add Recipe" else "Edit Recipe",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Terracotta,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Cream)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = ingredientsText,
                onValueChange = { ingredientsText = it },
                label = { Text("Ingredients (comma-separated)") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = cookingTime,
                onValueChange = { cookingTime = it },
                label = { Text("Cooking Time (minutes)") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )
            Spacer(Modifier.height(20.dp))

            Text("Category", style = MaterialTheme.typography.labelLarge, color = DeepBrown, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Category.entries.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Terracotta,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            Text("Rating", style = MaterialTheme.typography.labelLarge, color = DeepBrown, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            StarRatingInput(
                rating = rating,
                onRatingChange = { rating = it }
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val ingredientsList = ingredientsText.split(",").map { it.trim() }.filter { it.isNotBlank() }
                    onSave(title, description, ingredientsList, cookingTime.toIntOrNull() ?: 0, selectedCategory, rating)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Terracotta,
                    contentColor = Color.White
                ),
                enabled = title.isNotBlank()
            ) {
                Text(
                    if (existingRecipe == null) "Add Recipe" else "Save Changes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}