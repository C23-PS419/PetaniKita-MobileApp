package com.c23ps419.petanikita.di

import com.c23ps419.petanikita.data.DataRepository
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(userPreferences: UserPreferences): DataRepository {
        val apiService = ApiConfig.getApiService(userPreferences)
        return DataRepository(apiService, userPreferences)
    }
}