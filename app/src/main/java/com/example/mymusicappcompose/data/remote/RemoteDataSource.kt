package com.example.mymusicappcompose.data.remote

import com.example.mymusicappcompose.data.entity.AccessSpotifyToken
import com.example.mymusicappcompose.data.entity.SignInBody
import com.example.mymusicappcompose.data.entity.SignInResponse
import com.example.mymusicappcompose.data.entity.SignOutResponse
import com.example.mymusicappcompose.data.entity.SignUpBody
import com.example.mymusicappcompose.data.entity.SignUpResponse
import com.example.mymusicappcompose.data.entity.spotify.album.GetArtistAlbums
import com.example.mymusicappcompose.data.entity.spotify.genres.GetGenres
import com.example.mymusicappcompose.data.entity.spotify.recommendations.GetRecommendations
import com.example.mymusicappcompose.data.entity.spotify.artist.GetSearchArtists
import com.example.mymusicappcompose.data.entity.spotify.tracks.GetAlbumsTracks
import com.example.mymusicappcompose.data.network.API
import com.example.mymusicappcompose.data.network.AccessSpotifyAPI
import com.example.mymusicappcompose.data.network.NetworkProvider
import com.example.mymusicappcompose.data.network.SpotifyAPI

class RemoteDataSource {

    /**
     * Obtem o nosso acesso
     */
    private val api: API = NetworkProvider.api()

    /**
     * Obtem oacesso do Spotify
     */
    private val accessSpotifyApi: AccessSpotifyAPI = NetworkProvider.accessSpotifyApi()

    /**
     * Obtem os dados
     */
    private val spotifyApi: SpotifyAPI = NetworkProvider.spotifyApi()


    suspend fun signUp(email: String, password: String): SignUpResponse {
        return api.signUp(SignUpBody(email, password))
    }

    suspend fun signIn(email: String, password: String): SignInResponse {
        return api.signIn(SignInBody(email, password))
    }

    suspend fun signOut(): SignOutResponse {
        return api.signOut()
    }

    suspend fun getSpotifyToken(clientId: String, clientSecret: String): AccessSpotifyToken? {
        return accessSpotifyApi.token("client_credentials", clientId, clientSecret)
    }

    /**
     * Bearer + ' ' + access token
     */
    suspend fun getGenres(tokenType: String, accessToken: String): GetGenres {
        return spotifyApi.getGenres("$tokenType $accessToken")
    }

    suspend fun getRecommendations(
        tokenType: String,
        accessToken: String,
        genre: String
    ): GetRecommendations {
        return spotifyApi.getRecommendations("$tokenType $accessToken", genre)
    }

    suspend fun getSearchArtists(
        tokenType: String,
        accessToken: String,
        query: String
    ): GetSearchArtists {
        return spotifyApi.getSearchArtists("$tokenType $accessToken", query)
    }

    suspend fun getArtistAlbums(
        tokenType: String,
        accessToken: String,
        id: String
    ): GetArtistAlbums {
        return spotifyApi.getArtistsAlbums("$tokenType $accessToken", id)
    }

    suspend fun getAlbumTrack(
        tokenType: String,
        accessToken: String,
        id: String
    ): GetAlbumsTracks {
        return spotifyApi.getAlbumTracks("$tokenType $accessToken", id)
    }

}
