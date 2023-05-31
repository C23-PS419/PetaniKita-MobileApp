package com.c23ps419.petanikita.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps419.petanikita.data.remote.network.ApiService
import com.c23ps419.petanikita.data.remote.response.LoginResponse
import com.c23ps419.petanikita.data.remote.response.RegisterResponse
import retrofit2.Response

class DataRepository(private val apiService: ApiService) {
    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val deviceInfo = android.os.Build.MANUFACTURER + android.os.Build.PRODUCT
            val response = apiService.userLogin(email, password, deviceInfo)
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