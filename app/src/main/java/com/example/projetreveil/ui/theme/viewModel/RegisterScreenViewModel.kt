package com.example.projetreveil.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.SupabaseClient

import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Users
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.providers.builtin.Email

class RegisterScreenViewModel : ViewModel() {

    enum class AuthStateEnum { IDLE, LOADING, SUCCESS, ERROR }

    val repository = UsersRepository();
    var pseudo by mutableStateOf("");
    var mdp by mutableStateOf("");

    var state by mutableStateOf(AuthStateEnum.IDLE)

    var usersRepository = UsersRepository();

    fun Init() {
        pseudo = ""
        mdp = ""
    }

    val supabase = SupabaseClient.client
    
    private fun pseudoToEmail(pseudo: String) =
        "${pseudo.trim().lowercase()}@monapp.app"

    //En attendant de vraiment mettre le module de register
    fun registerUser() {

        if (this.state == AuthStateEnum.LOADING) return

        viewModelScope.launch {
            try {
                Log.i("Auth", "Inscription tentée")

                state = AuthStateEnum.LOADING
                supabase.auth.signUpWith(Email) {
                    this.email = pseudoToEmail(pseudo)       //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp

                    var user : Users = Users(id = supabase.auth.currentUserOrNull()?.id , name = pseudo, profilPicture = 0)
                    viewModelScope.launch {
                        usersRepository.add(user)
                    }                }
                state = AuthStateEnum.SUCCESS
            } catch (e: Exception) {
                state = AuthStateEnum.IDLE
                Log.e("Auth", "Inscription échouée", e)
            }
        }
    }


    fun connectUser() {
        if (this.state == AuthStateEnum.LOADING) return

        viewModelScope.launch {
            try {
                state = AuthStateEnum.LOADING
                supabase.auth.signInWith(Email) {
                    this.email = pseudoToEmail(pseudo)       //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp
                }
                state = AuthStateEnum.SUCCESS
            } catch (e: Exception) {
                state = AuthStateEnum.IDLE
                Log.e("Auth", "Inscription échouée", e)
            }
        }
    }
}