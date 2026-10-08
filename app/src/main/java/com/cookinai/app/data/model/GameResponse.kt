package com.cookinai.app.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
    val count: Int,
    val results: List<Game>
)
