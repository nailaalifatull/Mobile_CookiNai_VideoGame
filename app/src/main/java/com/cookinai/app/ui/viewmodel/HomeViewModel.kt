package com.cookinai.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookinai.app.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<GameListUiState>(GameListUiState.Loading)
    val uiState: StateFlow<GameListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchGames()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L) // debounce 500ms
            fetchGames(query)
        }
    }

    private fun fetchGames(query: String = "") {
        viewModelScope.launch {
            _uiState.value = GameListUiState.Loading
            try {
                val response = repository.getGames(search = query)
                _uiState.value = GameListUiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = GameListUiState.Error(
                    e.message ?: "Terjadi kesalahan saat memuat data."
                )
            }
        }
    }
}
