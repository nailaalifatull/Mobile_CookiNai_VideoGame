package com.cookinai.app.data.repository

import com.cookinai.app.data.model.GameDetail
import com.cookinai.app.data.model.GameResponse
import com.cookinai.app.data.remote.ApiClient

class GameRepository {

    private val apiService = ApiClient.apiService

    suspend fun getGames(search: String = ""): GameResponse {
        return apiService.getGames(search = search)
    }

    suspend fun getGameDetail(id: Int): GameDetail {
        return apiService.getGameDetail(id = id)
    }
}
