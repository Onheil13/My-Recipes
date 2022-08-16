package dev.quisto.info.data.repository

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.core.domain.repository.RESTDataSource
import dev.quisto.core.util.DEFAULT_TIME_OUT
import dev.quisto.info.domain.repository.InformationRepository
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ViewModelScoped
class InformationRepositoryImpl @Inject constructor(
    private val retrofitClient: RESTDataSource,
    ): InformationRepository {
    override suspend fun getInformation(recipeId: Int) = callbackFlow{

        trySend(UiState.Loading)

        try {
            val response = kotlinx.coroutines.withTimeout(DEFAULT_TIME_OUT){
                retrofitClient.getRecipeInformation(recipeId =recipeId)
            }

            if (response.code() >= 500) {
                trySend(UiState.Error("Server error has occurred."))
            } else if (response.code() >= 400) {
                trySend(UiState.Error("Server error has occurred."))
            } else if (response.code() == 200) {
                trySend(UiState.Success(response.body()))
            } else {
                trySend(UiState.Error("Server error has occurred."))
            }

        }
        catch (timeOut: TimeoutCancellationException) {
            trySend(UiState.Error("Connection timeout"))
        } catch (exception: Exception) {
            if (exception.message.toString().contains("No address associated with hostname")) {
                trySend(UiState.Error("Server error has occurred."))
            } else {
                trySend(UiState.Error("Server error has occurred."))
            }
        }

        awaitClose {  }
    }
}