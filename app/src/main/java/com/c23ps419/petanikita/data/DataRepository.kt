package com.c23ps419.petanikita.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.network.ApiService
import com.c23ps419.petanikita.data.remote.response.LoginResponse
import com.c23ps419.petanikita.data.remote.response.RegisterResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class DataRepository(private val apiService: ApiService, private val userPreferences: UserPreferences) {
    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val deviceInfo = android.os.Build.MANUFACTURER + android.os.Build.PRODUCT
            val response = apiService.userLogin(email, password, deviceInfo)

            runBlocking {
                if (response.token != null){
                    userPreferences.saveUserLoginData(response.token, true)
                    Log.d("postLogin","userPreferences ${response.token}")
                }
            }
            emit(Result.Success(response))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(name: String, email: String, phone: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userRegister(name, email, phone, password)
            emit(Result.Success(response))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getLogout(token: String): LiveData<Result<Response<Unit>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userLogout(token)
            if (response.isSuccessful){
                emit(Result.Success(response))
            }
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}