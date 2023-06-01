package com.c23ps419.petanikita.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.network.ApiService
import com.c23ps419.petanikita.data.remote.response.LoginResponse
import com.c23ps419.petanikita.data.remote.response.LogoutResponse
import com.c23ps419.petanikita.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DataRepository(private val apiService: ApiService, private val userPreferences: UserPreferences) {
    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val deviceInfo = android.os.Build.MANUFACTURER + android.os.Build.PRODUCT
            val response = apiService.userLogin(email, password, deviceInfo)

            runBlocking {
                if (response.token != null){
                    userPreferences.saveUserLoginData(response.token, true)
                    Log.d("postLogin","saveUserLoginData ${response.token}")
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

    fun getLogout(): LiveData<Result<LogoutResponse>> = liveData {

        emit(Result.Loading)
        try {
            val token = runBlocking {
                userPreferences.getUserToken().first()
            }
            val response = apiService.userLogout(token)
            runBlocking {
                userPreferences.onUserLogout()
                Log.d("getLogout", "saveUserLoginData")
            }

            emit(Result.Success(response))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}