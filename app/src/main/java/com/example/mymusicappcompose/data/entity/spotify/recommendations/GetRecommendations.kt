package com.example.mymusicappcompose.data.entity.spotify.recommendations

data class GetRecommendations(
    val tracks: List<TrackRecommend>,
    val seeds: List<SeedRecommend>
)

