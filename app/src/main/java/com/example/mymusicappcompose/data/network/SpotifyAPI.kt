package com.example.mymusicappcompose.data.network

import com.example.mymusicappcompose.data.entity.spotify.album.GetArtistAlbums
import com.example.mymusicappcompose.data.entity.spotify.genres.GetGenres
import com.example.mymusicappcompose.data.entity.spotify.recommendations.GetRecommendations
import com.example.mymusicappcompose.data.entity.spotify.artist.GetSearchArtists
import com.example.mymusicappcompose.data.entity.spotify.tracks.GetAlbumsTracks
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyAPI {

    @GET("recommendations/available-genre-seeds")
    suspend fun getGenres(
        @Header(NetworkProvider.AUTHORIZATION) authorization: String
    ): GetGenres

    @GET("recommendations")
    suspend fun getRecommendations(
        @Header(NetworkProvider.AUTHORIZATION) authorization: String,
        @Query("seed_genres") genre: String,
        @Query("market") market: String = "GR",
        @Query("limit") limit: Int = 10
    ): GetRecommendations

    @GET("search")
    suspend fun getSearchArtists(
        @Header(NetworkProvider.AUTHORIZATION) authorization: String,
        @Query("q") query: String,
        @Query("type") type: String = "artist",
        @Query("market") market: String = "GR",
        @Query("limit") limit: Int = 10
    ): GetSearchArtists

    @GET("artists/{id}/albums")
    suspend fun getArtistsAlbums(
        @Header(NetworkProvider.AUTHORIZATION) authorization: String,
        @Path("id") id: String,
        @Query("include_groups") include: String = "album",
        @Query("market") market: String = "GR",
        @Query("limit") limit: Int = 50

    ): GetArtistAlbums

    @GET("albums/{id}/tracks")
    suspend fun getAlbumTracks(
        @Header(NetworkProvider.AUTHORIZATION) authorization: String,
        @Path("id") id: String,
        @Query("market") market: String = "GR",
        @Query("limit") limit: Int = 50
    ): GetAlbumsTracks
}
