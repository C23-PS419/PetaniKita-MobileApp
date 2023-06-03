package com.c23ps419.petanikita.di

import android.app.Application
import com.c23ps419.petanikita.data.DataRepository
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.network.ApiConfig
import com.c23ps419.petanikita.data.repository.UserDatabaseRepository

object Injection {
    fun provideRepository(userPreferences: UserPreferences): DataRepository {
        val apiService = ApiConfig.getApiService(userPreferences)
        return DataRepository(apiService, userPreferences)
    }

    fun provideUserDatabaseRepository(userPreferences: UserPreferences, application: Application): UserDatabaseRepository {
        val apiService = ApiConfig.getApiService(userPreferences)
        return UserDatabaseRepository(application,apiService, userPreferences)
    }
}