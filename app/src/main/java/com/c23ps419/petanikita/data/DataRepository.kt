package com.c23ps419.petanikita.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps419.petanikita.data.remote.network.ApiService
import com.c23ps419.petanikita.data.remote.response.LoginResponse

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
}