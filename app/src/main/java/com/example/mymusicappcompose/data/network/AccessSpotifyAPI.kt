package com.example.mymusicappcompose.data.network

import com.example.mymusicappcompose.data.entity.AccessSpotifyToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Interface que realiza o MAPEAMENTO dos endpoints de uma API (Servidor) Nesse caso a api acesso spotify
 */
interface AccessSpotifyAPI {

    @FormUrlEncoded
    @POST("token")
    suspend fun token(
        @Field("grant_type") grant_type: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String
    ): AccessSpotifyToken
}