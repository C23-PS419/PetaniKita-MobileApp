package com.c23ps419.petanikita.ui.register

import androidx.lifecycle.ViewModel
import com.c23ps419.petanikita.data.DataRepository

class RegisterViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun userRegister(name: String, email: String, password: String) = dataRepository.postRegister(name, email, password)
}