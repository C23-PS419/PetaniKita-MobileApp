package com.c23ps419.petanikita.ui.main.home

import androidx.lifecycle.ViewModel
import com.c23ps419.petanikita.data.repository.UserDatabaseRepository

class HomeViewModel(private val userDatabaseRepository: UserDatabaseRepository) : ViewModel() {

    fun allProducts() = userDatabaseRepository.getAllProducts()

}