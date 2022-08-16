package dev.quisto.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.quisto.core.domain.model.UiState
import dev.quisto.recipes.domain.use_case.RecipeListUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeListUseCases: RecipeListUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any?>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            recipeListUseCases.getRecipes().collect { response ->
                _uiState.update {
                    response
                }
            }
        }
    }
}