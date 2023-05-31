package com.c23ps419.petanikita.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps419.petanikita.di.Injection
import com.c23ps419.petanikita.ui.login.LoginViewModel
import com.c23ps419.petanikita.ui.mainhome.MainViewModel
import com.c23ps419.petanikita.ui.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}