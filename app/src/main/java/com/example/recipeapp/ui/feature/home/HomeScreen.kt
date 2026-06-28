package com.example.recipeapp.ui.feature.home

import com.example.recipeapp.R
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onBrowseClicked : () -> Unit,
    onFavouritesClicked : () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Recipe App",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.home_screen),
                contentDescription = "Women Cooking",
                alignment = Alignment.Center,
                modifier = Modifier.height(200.dp)
            )
            Spacer(Modifier.height(32.dp))
            Button(onClick = onBrowseClicked) {
                Text(text = "Browse Recipes")
            }
            Button(onClick = onFavouritesClicked) {
                Text(text = "Favourites")
            }
        }
    }
}