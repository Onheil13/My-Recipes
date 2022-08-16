package dev.quisto.recipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.quisto.core.domain.repository.RESTDataSource
import dev.quisto.info.data.repository.InformationRepositoryImpl
import dev.quisto.info.domain.repository.InformationRepository
import dev.quisto.info.domain.use_case.GetInformation
import dev.quisto.info.domain.use_case.InformationUseCases
import dev.quisto.recipes.data.repository.RecipeListRepositoryImpl
import dev.quisto.recipes.domain.repository.RecipeListRepository
import dev.quisto.recipes.domain.use_case.GetRecipes
import dev.quisto.recipes.domain.use_case.RecipeListUseCases
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object RecipeListModule {

    @Provides
    fun provideInfoRepo(
        @Named("retrofitClient")
        retrofitClient: RESTDataSource,
    ): InformationRepository = InformationRepositoryImpl(
        retrofitClient = retrofitClient
    )

    @Provides
    fun provideInfoUseCase(
        informationRepository: InformationRepository,
    ) = InformationUseCases(
        getInformation = GetInformation(informationRepository = informationRepository)
    )
}