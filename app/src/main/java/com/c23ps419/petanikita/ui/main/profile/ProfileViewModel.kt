package com.c23ps419.petanikita.ui.main.profile

import androidx.lifecycle.ViewModel
import com.c23ps419.petanikita.data.DataRepository

class ProfileViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun userLogout() = dataRepository.getLogout()
}