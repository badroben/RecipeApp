package com.example.recipeapp.ui.feature.recipeDetails

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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.data.local.entity.RecipeEntity

private val Terracotta = Color(0xFFC65D3B)      // primary accent
private val TerracottaDark = Color(0xFF9E4327)  // pressed / deep accent
private val Cream = Color(0xFFFBF3E9)           // page + card surface
private val DeepBrown = Color(0xFF3B2A20)       // primary text on cream
private val StarColor = Color(0xFFF2A93B)       // warm gold, sits in the same family

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipe: RecipeEntity,
    onAddToFavouritesClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onRemoveFromFavouritesClicked: () -> Unit,
    onHomeButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    isFavourite: Boolean = false
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = Cream,
            titleContentColor = DeepBrown,
            textContentColor = DeepBrown,
            title = { Text("Delete recipe?") },
            text = { Text("This will permanently remove \"${recipe.title}\". This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDeleteClicked()
                }) {
                    Text("Delete", color = Terracotta, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = DeepBrown)
                }
            }
        )
    }

    Scaffold(
        containerColor = Cream,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Recipe Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditClicked) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit recipe")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete recipe")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Terracotta,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarButton(
                        icon = Icons.Default.Home,
                        label = "Home",
                        onClick = onHomeButtonClicked,
                        modifier = Modifier.weight(1f)
                    )
                    BottomBarButton(
                        icon = Icons.Default.Favorite,
                        label = "Favourites",
                        onClick = onFavouriteClicked,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (!isFavourite) onAddToFavouritesClicked() else onRemoveFromFavouritesClicked()
                },
                containerColor = TerracottaDark,
                contentColor = Color.White,
                icon = {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                },
                text = { Text(if (isFavourite) "Remove" else "Favourite") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(Cream)
        ) {
            item { RecipeDetails(recipe) }
        }
    }
}

@Composable
fun RecipeDetails(recipe: RecipeEntity) {
    Column(modifier = Modifier.fillMaxSize()) {

        // ----- Header image with title + rating overlaid -----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(recipe.image),
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.35f),
                                Color.Black.copy(alpha = 0.75f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                StarRatingDisplay(rating = recipe.rating, starSize = 22.dp)
            }
        }

        Spacer(Modifier.height(18.dp))

        // ----- Quick facts: category + cooking time -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoPill(
                icon = Icons.Default.Restaurant,
                label = "Category",
                value = recipe.category.displayName,
                modifier = Modifier.weight(1f)
            )
            InfoPill(
                icon = Icons.Default.AccessTime,
                label = "Cooking time",
                value = "${recipe.cookingTime} min",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(22.dp))

        SectionCard(title = "Ingredients") {
            recipe.ingredients.forEach { ingredient ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Terracotta, shape = RoundedCornerShape(3.dp))
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.bodyLarge,
                        color = DeepBrown
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        SectionCard(title = "Description") {
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyLarge,
                color = DeepBrown.copy(alpha = 0.85f),
                lineHeight = 24.sp
            )
        }

        Spacer(Modifier.height(28.dp))
    }
}

@Composable
private fun BottomBarButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.18f),
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun InfoPill(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Terracotta.copy(alpha = 0.12f),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = Terracotta,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = DeepBrown.copy(alpha = 0.6f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = DeepBrown
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 22.dp)
                        .background(Terracotta, shape = RoundedCornerShape(2.dp))
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = DeepBrown
                )
            }
            Spacer(Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
fun StarRatingDisplay(
    rating: Int,
    starSize: Dp = 16.dp
) {
    Row {
        for (star in 1..5) {
            Icon(
                imageVector = if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = if (star <= rating) StarColor else Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(starSize)
            )
        }
    }
}