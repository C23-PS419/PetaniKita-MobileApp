package com.c23ps419.petanikita.di

import com.c23ps419.petanikita.data.DataRepository
import com.c23ps419.petanikita.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }
}