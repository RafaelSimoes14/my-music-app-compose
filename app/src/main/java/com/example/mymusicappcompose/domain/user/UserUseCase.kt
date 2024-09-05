package com.example.mymusicappcompose.domain.user

import com.example.mymusicappcompose.data.local.LocalDataSource
import com.example.mymusicappcompose.data.remote.RemoteDataSource
import com.example.mymusicappcompose.log.logDebug
import java.util.Date
import com.example.mymusicappcompose.core.Result

class UserUseCase {
    private val remoteDataSource = RemoteDataSource()

    suspend fun signIn(email: String, password: String): Result<Boolean> {
        try {
            val response = remoteDataSource.signIn(email, password)

            if (response.clientId.isNullOrEmpty() || response.clientSecret.isNullOrEmpty()) {
                return Result.Error(Throwable("ClientId or ClientSecret invalids!"))
            }

            val clientId = response.clientId
            val clientSecret = response.clientSecret

            val responseSpotify = remoteDataSource.getSpotifyToken(clientId, clientSecret)
            if (
                responseSpotify == null ||
                responseSpotify.access_token.isNullOrEmpty() ||
                responseSpotify.token_type.isNullOrEmpty() ||
                responseSpotify.expires_in == null
            ) {
                return Result.Error(Throwable("Spotify's ids invalids!"))
            }

            val expiresIn = responseSpotify.expires_in
            val timeNowSec = Date().time.div(1000)
            val expiredAt = timeNowSec + expiresIn

            val localDataSource = LocalDataSource.getInstance()
            localDataSource?.saveCredentials(clientId, clientSecret)

            localDataSource?.saveSpotify(
                responseSpotify.access_token.toString(),
                responseSpotify.token_type.toString(),
                expiredAt
            )

            return Result.Success(true)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun signUp(email: String, password: String): Result<Boolean> {
        try {
            val response = remoteDataSource.signUp(email, password)

            if (response.clientId.isNullOrEmpty() || response.clientSecret.isNullOrEmpty()) {
                return Result.Error(Throwable("ClientId or ClientSecret invalids!"))
            }
            val clientId = response.clientId
            val clientSecret = response.clientSecret

            logDebug("$clientId : $clientSecret")
            val responseSpotify = remoteDataSource.getSpotifyToken(clientId, clientSecret)
            if (
                responseSpotify == null ||
                responseSpotify.access_token.isNullOrEmpty() ||
                responseSpotify.token_type.isNullOrEmpty() ||
                responseSpotify.expires_in == null
            ) {
                return Result.Error(Throwable("Spotify's ids invalids!"))
            }

            val expiresIn = responseSpotify.expires_in
            val timeNowSec = Date().time.div(1000)
            val expiredAt = timeNowSec + expiresIn

            val localDataSource = LocalDataSource.getInstance()
            localDataSource?.saveCredentials(clientId, clientSecret)

            localDataSource?.saveSpotify(
                responseSpotify.access_token.toString(),
                responseSpotify.token_type.toString(),
                expiredAt
            )

            return Result.Success(true)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }

    suspend fun isAuthorized(): Result<Boolean> {
        try {
            val clientId = LocalDataSource.getInstance()?.getClientId()
            val clientSecret = LocalDataSource.getInstance()?.getClientSecret()
            val accessToken = LocalDataSource.getInstance()?.getSpotifyAccessToken()
            val tokenType = LocalDataSource.getInstance()?.getSpotifyTokenType()
            val expiredAt = LocalDataSource.getInstance()?.getSpotifyExpiredAt()

            if (clientId.isNullOrEmpty() ||
                clientSecret.isNullOrEmpty() ||
                accessToken.isNullOrEmpty() ||
                tokenType.isNullOrEmpty() ||
                expiredAt == null
            ) {
                return Result.Success(false)
            }
            val timeNowSec = Date().time.div(1000)

            if (expiredAt < timeNowSec) {
                val responseSpotify = remoteDataSource.getSpotifyToken(clientId, clientSecret)

                if (responseSpotify == null ||
                    responseSpotify.access_token.isNullOrEmpty() ||
                    responseSpotify.token_type.isNullOrEmpty() ||
                    responseSpotify.expires_in == null
                ) {

                    return Result.Error(Throwable("Spotify's ids invalids!"))
                }
                val expiresIn = responseSpotify.expires_in
                val expiredAtNew = timeNowSec + expiresIn

                LocalDataSource.getInstance()?.saveSpotify(
                    responseSpotify.access_token.toString(),
                    responseSpotify.token_type.toString(),
                    expiredAtNew
                )
            }

            return Result.Success(true)

        } catch (t: Throwable) {
            return Result.Error(t)
        }

    }

    suspend fun signOut(): Result<Boolean> {
        try {
            val response = remoteDataSource.signOut()

            if (response.success) {
                LocalDataSource.getInstance()?.deleteCredentials()
                LocalDataSource.getInstance()?.deleteSpotify()
            }

            return Result.Success(true)

        } catch (t: Throwable) {
            return Result.Error(t)
        }
    }
}