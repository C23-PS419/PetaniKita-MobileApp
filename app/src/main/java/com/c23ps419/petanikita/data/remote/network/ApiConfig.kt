package com.c23ps419.petanikita.data.remote.network

import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private fun getInterceptor(token: String?): OkHttpClient{
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }
        fun getApiService(userPreferences: UserPreferences): ApiService {
            val token = runBlocking {
                userPreferences.getUserToken().first()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}