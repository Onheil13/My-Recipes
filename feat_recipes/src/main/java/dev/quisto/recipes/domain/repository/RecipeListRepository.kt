package dev.quisto.recipes.domain.repository

import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.receipe.Recipes
import kotlinx.coroutines.flow.Flow

interface RecipeListRepository {
    fun getRecipes() : Flow<UiState<Any?>>
}