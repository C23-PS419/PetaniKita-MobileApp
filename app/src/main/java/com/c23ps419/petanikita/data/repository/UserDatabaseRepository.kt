package com.c23ps419.petanikita.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c23ps419.petanikita.data.Result
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.local.roomdb.UserDao
import com.c23ps419.petanikita.data.local.roomdb.UserDatabase
import com.c23ps419.petanikita.data.remote.network.ApiService
import com.c23ps419.petanikita.data.remote.response.LogoutResponse
import com.c23ps419.petanikita.data.remote.response.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserDatabaseRepository(application: Application, private val apiService: ApiService, private val userPreferences: UserPreferences) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    val userData: LiveData<User> = mUserDao.getUserData()

    suspend fun refreshUserData(){
        withContext(Dispatchers.IO){
            val user = async { apiService.userInfo() }
            val result = user.await().data
            insertDataToLocal(result)
        }
    }

    private fun insertDataToLocal(user: User?){
        try {
            user?.let {
                executorService.execute { mUserDao.insert(user) }
            }
        } catch (e: Exception){
            Log.e("UserDataRepository", "error saving user data ${e.message}")
        }
    }

    private fun onLogoutDelete(){
        executorService.execute { mUserDao.onLogoutDeleteTableData() }
    }

    fun getLogout(): LiveData<Result<LogoutResponse>> = liveData {
        emit(Result.Loading)

        runBlocking {
            userPreferences.onUserLogout()
            onLogoutDelete()
            Log.d("getLogout", "saveUserLoginData")
        }

        try {
            val response = apiService.userLogout()
            emit(Result.Success(response))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}