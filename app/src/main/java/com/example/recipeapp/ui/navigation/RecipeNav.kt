package com.example.recipeapp.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipeapp.ui.feature.recipes.RecipesViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier.Companion.any
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.ui.feature.favourites.FavouritesScreen
import com.example.recipeapp.ui.feature.favourites.FavouritesViewModel
import com.example.recipeapp.ui.feature.home.HomeScreen
import com.example.recipeapp.ui.feature.insert.AddEditRecipeScreen
import com.example.recipeapp.ui.feature.recipeDetails.RecipeDetailsScreen
import com.example.recipeapp.ui.feature.recipes.RecipesScreen
import com.example.recipeapp.R


@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    favouritesViewModel: FavouritesViewModel = hiltViewModel()
){
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier,
        // 1. ENTER: When going TO a new screen (Forward)
        // Slide in from the Right edge
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400) // 400ms duration
            )
        },

        // 2. EXIT: The screen being covered moves slightly to the Left
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        // 3. POP ENTER: When coming BACK to a screen
        // The previous screen slides back in from the Left
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        // 4. POP EXIT: When leaving the current screen to go BACK
        // The current screen slides away to the Right
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {
        // ----------------------------
        // HOME SCREEN
        // ----------------------------
        composable(route = Routes.Home.route) {
            HomeScreen(
                onBrowseClicked = {
                    navController.navigate(route = Routes.Recipes.route)
                },
                onFavouritesClicked = {
                    navController.navigate(route = Routes.Favourites.route)
                }
            )
        }

        // ----------------------------
        // RECIPES SCREEN
        // ----------------------------
        composable(
            route = Routes.Recipes.route
        ) {
            RecipesScreen(
                onBackClicked = {navController.popBackStack()},
                onRecipeClick = { recipe ->
                    navController.navigate(Routes.RecipeDetails.createRoute(recipe.id))
                },
                onAddRecipeClicked = {
                    navController.navigate(Routes.AddEditRecipe.createAddRoute())
                }
            )
        }

        // ----------------------------
        // RECIPE DETAILS SCREEN
        // ----------------------------
        composable(
            route = Routes.RecipeDetails.route, // "recipe_details/{recipeId}"
            arguments = listOf(navArgument("recipeId") {
                type = NavType.IntType
            })
        )
        { backStackEntry ->

            val recipeId = backStackEntry.arguments?.getInt(Routes.RecipeDetails.ARG_RECIPE_ID)
            val uiState by recipesViewModel.uiState.collectAsState()
            val favUiState by favouritesViewModel.uiState.collectAsState()
            val recipe = uiState.recipes.find { it.id == recipeId }
            val isFavourite = recipe != null && favUiState.favourites.any { it.id == recipe.id }

            // Null Safety Check
            if (recipe != null) {
                RecipeDetailsScreen(
                    recipe = recipe,
                    isFavourite = isFavourite,
                    onAddToFavouritesClicked = { favouritesViewModel.toggleFavourite(recipe) },
                    onRemoveFromFavouritesClicked = { favouritesViewModel.toggleFavourite(recipe) },
                    onHomeButtonClicked = {
                        navController.popBackStack(Routes.Home.route, inclusive = false)
                    },
                    onFavouriteClicked = { navController.navigate(Routes.Favourites.route) },
                    onBackClicked = {navController.popBackStack()},
                    onEditClicked = {
                        navController.navigate(Routes.AddEditRecipe.createEditRoute(recipe.id))
                    },
                    onDeleteClicked = {
                        recipesViewModel.deleteRecipe(recipe)
                        navController.popBackStack()
                    }
                )
            } else {
                // Fallback for errors
                Text("Error: Recipe not found", modifier = Modifier.padding(16.dp))
            }
        }
        // ----------------------------
        // FAVOURITES SCREEN
        // ----------------------------
        composable(route = Routes.Favourites.route) {
            FavouritesScreen(
                onHomeButtonClicked = {
                    navController.popBackStack(Routes.Home.route, inclusive = false)
                },
                onRecipeClick = { recipe ->
                    navController.navigate(Routes.RecipeDetails.createRoute(recipe.id))
                },
                onBackClicked = {navController.popBackStack()}
            )
        }
        // ----------------------------
        // ADD AND EDIT SCREEN
        // ----------------------------
        composable(
            route = Routes.AddEditRecipe.route,
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.IntType
                    defaultValue = -1   // sentinel for "no ID = adding new"
                }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(Routes.AddEditRecipe.ARG_RECIPE_ID) ?: -1
            val uiState by recipesViewModel.uiState.collectAsState()
            val existingRecipe = if (recipeId != -1) uiState.recipes.find { it.id == recipeId } else null

            AddEditRecipeScreen(
                existingRecipe = existingRecipe,
                onSave = { title, description, ingredients ->
                    if (existingRecipe == null) {
                        recipesViewModel.addRecipe(title, description, ingredients, image = R.drawable.place_holder)
                    } else {
                        recipesViewModel.updateRecipe(
                            existingRecipe.copy(title = title, description = description, ingredients = ingredients)
                        )
                    }
                    navController.popBackStack()
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}