package dev.quisto.core.domain.model

sealed class UiState<out T>{
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data : T): UiState<T>()
    data class Error<T>(val error : T): UiState<T>()
}
