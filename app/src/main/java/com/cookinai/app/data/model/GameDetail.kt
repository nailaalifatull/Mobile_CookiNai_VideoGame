package com.cookinai.app.data.model

import com.google.gson.annotations.SerializedName

data class GameDetail(
    val id: Int,
    val name: String,
    val rating: Double,
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("description_raw")
    val descriptionRaw: String?,
    val genres: List<Genre>?,
    val platforms: List<PlatformWrapper>?,
    @SerializedName("ratings_count")
    val ratingsCount: Int?
)
