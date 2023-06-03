package com.c23ps419.petanikita.ui.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps419.petanikita.data.repository.UserDatabaseRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userDatabaseRepository: UserDatabaseRepository): ViewModel() {

    val user = userDatabaseRepository.userData

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try {
                userDatabaseRepository.refreshUserData()
            } catch (e: Exception){
                Log.e(TAG,e.message.toString())
            }
        }
    }

    fun userLogout() = userDatabaseRepository.getLogout()

    companion object {
        const val TAG = "ProfileViewModel"
    }
}