package com.example.mymusicappcompose.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkProvider {

    private const val ACCESS_SPOTIFY_URL = "https://accounts.spotify.com/api/"
    private const val SPOTIFY_URL = "https://api.spotify.com/v1/"
    private const val MOCK_URL_BASE = "https://mymusicapp.wiremockapi.cloud/"

    const val AUTHORIZATION = "Authorization"

    private fun retrofit(
        urlBase: String
    ): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    fun api(): API {
        return retrofit(MOCK_URL_BASE).create(API::class.java)
    }

    fun accessSpotifyApi(): AccessSpotifyAPI {
        return retrofit(ACCESS_SPOTIFY_URL).create(AccessSpotifyAPI::class.java)
    }

    fun spotifyApi(): SpotifyAPI {
        return retrofit(SPOTIFY_URL).create(SpotifyAPI::class.java)
    }
}
