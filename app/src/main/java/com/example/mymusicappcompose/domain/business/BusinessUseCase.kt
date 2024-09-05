package com.example.mymusicappcompose.domain.business

import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.data.entity.spotify.recommendations.TrackRecommend
import com.example.mymusicappcompose.data.entity.spotify.tracks.Track
import com.example.mymusicappcompose.data.local.LocalDataSource
import com.example.mymusicappcompose.data.remote.RemoteDataSource
import com.example.mymusicappcompose.log.logDebug

class BusinessUseCase {
    private val remoteDataSource = RemoteDataSource()

    suspend fun getGenres(): Result<List<String>> {
        try {
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()

            if (tokenType.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                return Result.Error(Throwable("Spotify's tokens invalids!"))
            }

            val response = remoteDataSource.getGenres(tokenType, accessToken)
            return Result.Success(response.genres)
        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun getTracksRecommended(genre: String): Result<List<TrackRecommend>> {
        try {
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()

            if (tokenType.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                return Result.Error(Throwable("Spotify's tokens invalids!"))
            }
            val response = remoteDataSource.getRecommendations(tokenType, accessToken, genre)

            return Result.Success(response.tracks)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun getArtists(search: String): Result<List<Artist>> {
        logDebug("artist: $search")
        try {
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()

            if (tokenType.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                return Result.Error(Throwable("Spotify's tokens invalids!"))
            }

            val response = remoteDataSource.getSearchArtists(tokenType, accessToken, search)
            return Result.Success(response.artists.items)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun getAlbums(idArtist: String): Result<List<Album>> {
        try {
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()

            if (tokenType.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                return Result.Error(Throwable("Spotify's tokens invalids!"))
            }
            val response = remoteDataSource.getArtistAlbums(tokenType, accessToken, idArtist)
            return Result.Success(response.items)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun getTracks(idAlbums: String): Result<List<Track>> {
        try {
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()

            if (tokenType.isNullOrEmpty() || accessToken.isNullOrEmpty()) {
                return Result.Error(Throwable("Spotify's tokens invalids!"))
            }
            val response = remoteDataSource.getAlbumTrack(tokenType, accessToken, idAlbums)
            return Result.Success(response.items)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }
}

