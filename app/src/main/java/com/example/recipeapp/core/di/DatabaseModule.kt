package com.example.recipeapp.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipeapp.data.local.InitialRecipes
import com.example.recipeapp.data.local.RecipeDatabase
import com.example.recipeapp.data.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.jvm.java
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        recipeDaoProvider: Provider<RecipeDao>
    ): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_database"
        )
            .fallbackToDestructiveMigration(true)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        recipeDaoProvider.get().insertAll(InitialRecipes.list)
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao = database.recipeDao()
}