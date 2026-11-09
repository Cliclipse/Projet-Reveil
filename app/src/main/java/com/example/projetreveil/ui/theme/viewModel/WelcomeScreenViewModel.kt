package com.example.projetreveil.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Users
import kotlinx.coroutines.launch

class WelcomeScreenViewModel: ViewModel() {
    private val usersRepository : UsersRepository = UsersRepository()

    var currentUser by mutableStateOf<Users?>(null)
        private set

    fun loadCurrentUser(){
        viewModelScope.launch {
            try {
                currentUser = usersRepository.getCurrentUser()
            }
            catch (e: Exception){
                Log.e("WelcomeScreenViewModel", "Error getting user ${e.message}", e)
            }
        }
    }

    fun clearCurrentUser(){
        currentUser = null
    }
}