package com.example.mymusicappcompose.data.entity.spotify.recommendations

import com.google.gson.annotations.SerializedName

data class SeedRecommend(
    @SerializedName("initialPoolSize") val initialPoolSize : Int,
    @SerializedName("afterFilteringSize") val afterFilteringSize : Int,
    @SerializedName("afterRelinkingSize") val afterRelinkingSize : Int,
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("href") val href : String
)
