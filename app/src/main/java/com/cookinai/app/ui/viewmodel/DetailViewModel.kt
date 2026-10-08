package com.cookinai.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookinai.app.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<GameDetailUiState>(GameDetailUiState.Loading)
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()

    fun fetchGameDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = GameDetailUiState.Loading
            try {
                val game = repository.getGameDetail(id)
                _uiState.value = GameDetailUiState.Success(game)
            } catch (e: Exception) {
                _uiState.value = GameDetailUiState.Error(
                    e.message ?: "Gagal memuat detail game."
                )
            }
        }
    }
}
