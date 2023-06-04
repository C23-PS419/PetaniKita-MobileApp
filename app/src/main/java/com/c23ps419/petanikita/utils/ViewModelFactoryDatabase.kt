package com.c23ps419.petanikita.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps419.petanikita.data.local.datastore.UserPreferences
import com.c23ps419.petanikita.di.Injection
import com.c23ps419.petanikita.ui.main.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryDatabase(private val userPreferences: UserPreferences, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideUserDatabaseRepository(userPreferences, application)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}