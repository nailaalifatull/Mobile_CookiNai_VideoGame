package com.cookinai.app.data.model

import com.google.gson.annotations.SerializedName

data class Game(
    val id: Int,
    val name: String,
    val rating: Double,
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    val genres: List<Genre>?,
    val platforms: List<PlatformWrapper>?
)

data class Genre(
    val id: Int,
    val name: String
)

data class PlatformWrapper(
    val platform: Platform
)

data class Platform(
    val id: Int,
    val name: String
)
