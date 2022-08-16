package dev.quisto.core.domain.repository

import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.core.domain.model.receipe.Recipes
import dev.quisto.core.util.RECIPE_MAX_LIMIT
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RESTDataSource {

    @GET("random?number=${RECIPE_MAX_LIMIT}")
    suspend fun getRecipes(): Response<Recipes>

    @GET("{recipeId}/information?includeNutrition=false")
    suspend fun getRecipeInformation(@Path("recipeId") recipeId: Int): Response<Recipe>

}