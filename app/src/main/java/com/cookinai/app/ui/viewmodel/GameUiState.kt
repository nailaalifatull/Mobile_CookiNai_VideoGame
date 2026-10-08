package com.cookinai.app.ui.viewmodel

import com.cookinai.app.data.model.Game
import com.cookinai.app.data.model.GameDetail

sealed class GameListUiState {
    object Loading : GameListUiState()
    data class Success(val games: List<Game>) : GameListUiState()
    data class Error(val message: String) : GameListUiState()
}

sealed class GameDetailUiState {
    object Loading : GameDetailUiState()
    data class Success(val game: GameDetail) : GameDetailUiState()
    data class Error(val message: String) : GameDetailUiState()
}
