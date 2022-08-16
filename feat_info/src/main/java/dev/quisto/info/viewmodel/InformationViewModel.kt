package dev.quisto.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.quisto.core.domain.model.UiState
import dev.quisto.info.domain.use_case.InformationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val informationUseCases: InformationUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any?>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getRecipes(recipeId: Int) {
        viewModelScope.launch {
            informationUseCases.getInformation(recipeId = recipeId).collect { response ->
                _uiState.update {
                    response
                }
            }
        }
    }
}