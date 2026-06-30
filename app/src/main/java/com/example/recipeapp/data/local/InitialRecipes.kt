package com.example.recipeapp.data.local

import com.example.recipeapp.R
import com.example.recipeapp.data.local.entity.RecipeEntity
import com.example.recipeapp.domain.model.Category

object InitialRecipes {
    val list = listOf(
        RecipeEntity(
            id = 1,
            title = "Spaghetti Bolognese",
            description = "A classic Italian pasta dish with rich, savory meat sauce. Simple, hearty, and perfect for weeknights.",
            ingredients = listOf(
                "Pasta",
                "Ground beef",
                "Tomato sauce",
                "Onion",
                "Garlic",
                "Olive oil",
                "Salt",
                "Pepper"
            ),
            cookingTime = 60,
            category = Category.DINNER,
            rating = 4,
            isFavourite = false,
            image = R.drawable.spaghetti
        ),
        RecipeEntity(
            id = 2,
            title = "Chicken Curry",
            description = "A creamy, mildly spicy curry full of flavor. Great served with rice or naan.",
            ingredients = listOf(
                "Chicken",
                "Curry paste",
                "Coconut milk",
                "Onion",
                "Garlic",
                "Ginger",
                "Salt"
            ),
            cookingTime = 90,
            category = Category.DINNER,
            rating = 5,
            isFavourite = false,
            image = R.drawable.curry
        ),
        RecipeEntity(
            id = 3,
            title = "Vegetable Stir Fry",
            description = "A quick, colorful dish packed with fresh veggies. Light, healthy, and customizable.",
            ingredients = listOf(
                "Bell peppers",
                "Broccoli",
                "Carrots",
                "Soy sauce",
                "Garlic",
                "Sesame oil"
            ),
            cookingTime = 45,
            category = Category.DINNER,
            rating = 3,
            isFavourite = false,
            image = R.drawable.stir_fry
        ),
        RecipeEntity(
            id = 4,
            title = "Beef Tacos",
            description = "Crispy or soft tacos filled with seasoned beef. Easy crowd-pleaser for gatherings.",
            ingredients = listOf(
                "Ground beef",
                "Taco seasoning",
                "Taco shells",
                "Lettuce",
                "Cheese",
                "Tomatoes"
            ),
            cookingTime = 40,
            category = Category.DINNER,
            rating = 4,
            isFavourite = false,
            image = R.drawable.tacos
        ),
        RecipeEntity(
            id = 5,
            title = "Pancakes",
            description = "Fluffy, golden pancakes perfect for breakfast. Delicious with syrup or berries.",
            ingredients = listOf(
                "Flour",
                "Eggs",
                "Milk",
                "Sugar",
                "Butter",
                "Baking powder",
                "Salt"
            ),
            cookingTime = 30,
            category = Category.BREAKFAST,
            rating = 5,
            isFavourite = false,
            image = R.drawable.pancakes
        ),
        RecipeEntity(
            id = 6,
            title = "Caesar Salad",
            description = "A crisp and refreshing salad with creamy dressing. Great as a side or light meal.",
            ingredients = listOf(
                "Romaine lettuce",
                "Croutons",
                "Parmesan",
                "Caesar dressing",
                "Lemon"
            ),
            cookingTime = 35,
            category = Category.LUNCH,
            rating = 4,
            isFavourite = false,
            image = R.drawable.caesar_salad
        ),
        RecipeEntity(
            id = 7,
            title = "Tomato Soup",
            description = "Smooth, comforting tomato soup with a touch of cream. Perfect for cold days.",
            ingredients = listOf(
                "Tomatoes",
                "Onion",
                "Garlic",
                "Vegetable broth",
                "Cream",
                "Salt",
                "Pepper"
            ),
            cookingTime = 45,
            category = Category.LUNCH,
            rating = 2,
            isFavourite = false,
            image = R.drawable.tomato_soup
        ),
        RecipeEntity(
            id = 8,
            title = "Grilled Cheese Sandwich",
            description = "A crispy, buttery sandwich with gooey melted cheese. Quick and satisfying.",
            ingredients = listOf("Bread", "Butter", "Cheese slices"),
            cookingTime = 25,
            category = Category.SNACK,
            rating = 5,
            isFavourite = false,
            image = R.drawable.grilled_cheese
        ),
        RecipeEntity(
            id = 9,
            title = "Fried Rice",
            description = "A fast, flavorful dish using leftover rice. Customizable with veggies or meat.",
            ingredients = listOf(
                "Rice",
                "Eggs",
                "Soy sauce",
                "Peas",
                "Carrots",
                "Green onions",
                "Oil"
            ),
            cookingTime = 60,
            category = Category.LUNCH,
            rating = 4,
            isFavourite = false,
            image = R.drawable.fired_rice
        ),
        RecipeEntity(
            id = 10,
            title = "Chocolate Chip Cookies",
            description = "Warm, chewy cookies loaded with chocolate chips. A classic homemade treat.",
            ingredients = listOf(
                "Flour",
                "Sugar",
                "Brown sugar",
                "Butter",
                "Eggs",
                "Chocolate chips",
                "Baking soda",
                "Salt"
            ),
            cookingTime = 25,
            category = Category.DESSERT,
            rating = 5,
            isFavourite = false,
            image = R.drawable.cookies
        )
    )
}