package com.cookinai.app.data.remote

import com.cookinai.app.BuildConfig
import com.cookinai.app.data.model.GameDetail
import com.cookinai.app.data.model.GameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApiService {

    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = BuildConfig.RAWG_API_KEY,
        @Query("search") search: String = "",
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "-rating"
    ): GameResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.RAWG_API_KEY
    ): GameDetail
}
