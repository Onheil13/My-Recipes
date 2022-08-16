package dev.quisto.recipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.quisto.core.domain.repository.RESTDataSource
import dev.quisto.recipes.data.repository.RecipeListRepositoryImpl
import dev.quisto.recipes.domain.repository.RecipeListRepository
import dev.quisto.recipes.domain.use_case.GetRecipes
import dev.quisto.recipes.domain.use_case.RecipeListUseCases
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object InformationModule {

    @Provides
    fun provideRecipeRepo(
        @Named("retrofitClient")
        retrofitClient: RESTDataSource,
    ): RecipeListRepository = RecipeListRepositoryImpl(
        retrofitClient = retrofitClient
    )

    @Provides
    fun provideRecipeUseCase(
        recipeListRepository: RecipeListRepository
    ) = RecipeListUseCases(
        getRecipes = GetRecipes(recipeListRepository = recipeListRepository)
    )
}