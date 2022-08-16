package dev.quisto.recipes.data.repository

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.UiState.*
import dev.quisto.core.domain.repository.RESTDataSource
import dev.quisto.core.util.DEFAULT_TIME_OUT
import dev.quisto.recipes.domain.repository.RecipeListRepository
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.Error

@ViewModelScoped
class RecipeListRepositoryImpl @Inject constructor(
    private val retrofitClient: RESTDataSource,
) : RecipeListRepository {
    override fun getRecipes() = callbackFlow {
        trySend(Loading)

        try {
            val response = kotlinx.coroutines.withTimeout(DEFAULT_TIME_OUT){
                retrofitClient.getRecipes()
            }

            if (response.code() >= 500) {
                trySend(UiState.Error("Server error has occurred."))
            } else if (response.code() >= 400) {
                trySend(UiState.Error("Server error has occurred."))
            } else if (response.code() == 200) {
                trySend(Success(response.body()))
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

        awaitClose { }
    }
}