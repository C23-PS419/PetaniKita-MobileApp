package com.c23ps419.petanikita.ui.login

import androidx.lifecycle.ViewModel
import com.c23ps419.petanikita.data.repository.DataRepository

class LoginViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun userLogin(email: String, password: String) = dataRepository.postLogin(email, password)

    //TODO make function for onLogin: Save token and session into local data
}