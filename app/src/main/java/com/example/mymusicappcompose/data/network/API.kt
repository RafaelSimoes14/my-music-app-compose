package com.example.mymusicappcompose.data.network

import com.example.mymusicappcompose.data.entity.SignInBody
import com.example.mymusicappcompose.data.entity.SignInResponse
import com.example.mymusicappcompose.data.entity.SignOutResponse
import com.example.mymusicappcompose.data.entity.SignUpBody
import com.example.mymusicappcompose.data.entity.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface que realiza o MAPEAMENTO dos endpoints de uma API (Servidor)
 */
interface API {

    @POST("/json/sign-up")
    suspend fun signUp(@Body body: SignUpBody): SignUpResponse

    /**
     * Atividade fazer o mapeamento do sign-in e sign-out
     */
    @POST("/json/sign-in")
    suspend fun signIn(@Body body: SignInBody): SignInResponse

    @POST("/json/sign-out")
    suspend fun signOut(): SignOutResponse

}