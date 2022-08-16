package dev.quisto.info.domain.repository

import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.receipe.Recipe
import kotlinx.coroutines.flow.Flow

interface InformationRepository {
    suspend fun getInformation(recipeId : Int) : Flow<UiState<Any?>>
}