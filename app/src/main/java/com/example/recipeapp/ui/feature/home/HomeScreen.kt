package com.example.recipeapp.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import com.example.recipeapp.ui.theme.Cream
import com.example.recipeapp.ui.theme.DeepBrown
import com.example.recipeapp.ui.theme.Terracotta

@Composable
fun HomeScreen(
    onBrowseClicked: () -> Unit,
    onFavouritesClicked: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Cream) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ----- Hero image in a rounded frame with a warm gradient wash -----
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(28.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_screen),
                    contentDescription = "Cooking",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Terracotta.copy(alpha = 0.55f)
                                )
                            )
                        )
                )
            }

            Spacer(Modifier.height(36.dp))

            Text(
                text = "What's cooking\ntoday?",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = DeepBrown,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Browse your collection or jump straight to your favourites.",
                style = MaterialTheme.typography.bodyMedium,
                color = DeepBrown.copy(alpha = 0.65f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(36.dp))

            // ----- Primary action: Browse -----
            Button(
                onClick = onBrowseClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Terracotta,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null)
                Spacer(Modifier.size(10.dp))
                Text("Browse Recipes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(14.dp))

            // ----- Secondary action: Favourites -----
            OutlinedButton(
                onClick = onFavouritesClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Terracotta)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null)
                Spacer(Modifier.size(10.dp))
                Text("Favourites", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}