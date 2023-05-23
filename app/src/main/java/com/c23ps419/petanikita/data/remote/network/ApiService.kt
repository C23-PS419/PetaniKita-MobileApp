package com.c23ps419.petanikita.data.remote.network

import com.c23ps419.petanikita.data.remote.response.LoginResponse
import com.c23ps419.petanikita.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/token")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_name") deviceName: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse
}