package com.c23ps419.petanikita.data.remote.network

import com.c23ps419.petanikita.data.remote.response.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/token")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_name") deviceName: String
    ): LoginResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): RegisterResponse

    @Headers("Accept: application/json")
    @GET("auth/logout")
    suspend fun userLogout(): LogoutResponse

    @Headers("Accept: application/json")
    @GET("auth/user")
    suspend fun userInfo(): UserInformationResponse

    @Headers("Accept: application/json")
    @GET("products")
    suspend fun getAllProducts(): ProductResponse
}