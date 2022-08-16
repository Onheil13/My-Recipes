package dev.quisto.recipes.domain.use_case

import dev.quisto.recipes.domain.repository.RecipeListRepository

class GetRecipes(
    private val recipeListRepository: RecipeListRepository
) {
    operator fun invoke() = recipeListRepository.getRecipes()
}