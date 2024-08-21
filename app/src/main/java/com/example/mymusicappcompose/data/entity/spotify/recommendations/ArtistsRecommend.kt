package com.example.mymusicappcompose.data.entity.spotify.recommendations

import com.google.gson.annotations.SerializedName

data class ArtistsRecommend(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsRecommend,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)
