package com.c23ps419.petanikita.ui.register

import androidx.lifecycle.ViewModel
import com.c23ps419.petanikita.data.repository.DataRepository

class RegisterViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun userRegister(name: String, email: String, phone: String, password: String) = dataRepository.postRegister(name, email, phone, password)
}