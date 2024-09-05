package com.example.mymusicappcompose.data.local

import android.content.Context
import com.example.mymusicappcompose.data.keyvalue.KeyValueData
import java.lang.ref.WeakReference

class LocalDataSource(context: WeakReference<Context>) {

    private val keyValueData = KeyValueData(context, "mymusic-shared-preferences")

    private val clientId = "clientId"
    private val clientSecret = "clientSecret"

    private val spotifyAccessToken = "spotifyAccessToken"
    private val spotifyTokenType = "spotifyTokenType"
    private val spotifyExpiredAt = "spotifyExpiredAt"

    /**
     * Credenciais values
     */
    fun getClientSecret() = keyValueData.readString(clientSecret)
    fun getClientId() = keyValueData.readString(clientId)
    fun saveCredentials(id: String, secret: String): Boolean {
        keyValueData.write(clientId, id)
        keyValueData.write(clientSecret, secret)
        return true
    }

    fun deleteCredentials() = keyValueData.remove(clientId) && keyValueData.remove(clientSecret)


    /**
     * Spotify values Acess
     */
    fun getSpotifyAccessToken() = keyValueData.readString(spotifyAccessToken)
    fun getSpotifyTokenType() = keyValueData.readString(spotifyTokenType)
    fun getSpotifyExpiredAt() = keyValueData.readLong(spotifyExpiredAt)
    fun saveSpotify(accessToken: String, tokenType: String, expiredAt: Long): Boolean {
        keyValueData.write(spotifyAccessToken, accessToken)
        keyValueData.write(spotifyTokenType, tokenType)
        keyValueData.write(spotifyExpiredAt, expiredAt)
        return true
    }

    fun deleteSpotify() =
        keyValueData.remove(spotifyAccessToken) && keyValueData.remove(spotifyTokenType) && keyValueData.remove(
            spotifyExpiredAt
        )

    companion object {
        private var dataSource: LocalDataSource? = null
        fun getInstance() = dataSource

        fun init(context: Context): LocalDataSource? {
            if (dataSource == null) {
                dataSource = LocalDataSource(WeakReference(context))
            }
            return dataSource
        }
    }
}