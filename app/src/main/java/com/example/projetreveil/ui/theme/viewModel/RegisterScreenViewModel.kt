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

class RegisterScreenViewModel : ViewModel() {

    val repository = UsersRepository();
    var pseudo by mutableStateOf("");
    var mdp by mutableStateOf("");

    fun Init(){
        pseudo = ""
        mdp = ""
   }

    //En attendant de vraiment mettre le module de register
    fun registerUser(name: String) {
        viewModelScope.launch {
            try {
                val newUser = Users(name = name, profilPicture = 8)
                repository.add(newUser)
            } catch (e: Exception) {
                Log.d("test", "pas marche")
            }
        }
    }
}