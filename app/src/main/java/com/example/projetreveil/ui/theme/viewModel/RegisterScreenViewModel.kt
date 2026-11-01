package com.example.projetreveil.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.BuildConfig.SUPABASE_ANON_KEY
import com.example.projetreveil.BuildConfig.SUPABASE_URL
import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Users
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.providers.builtin.Email

class RegisterScreenViewModel : ViewModel() {

    val repository = UsersRepository();
    var pseudo by mutableStateOf("");
    var mdp by mutableStateOf("");

    var connexionSuccess = false;

    fun Init() {
        pseudo = ""
        mdp = ""
    }

    val supabase = createSupabaseClient(SUPABASE_URL, SUPABASE_ANON_KEY) { install(Auth) }


    private fun pseudoToEmail(pseudo: String) =
        "${pseudo.trim().lowercase()}@monapp.app"

    //En attendant de vraiment mettre le module de register
    fun registerUser() {
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = pseudoToEmail(pseudo)       //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp
                }
                connexionSuccess = true
            } catch (e: Exception) { }
        }
    }


    fun connectUser() {
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = pseudoToEmail(pseudo)       //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp
                }
                connexionSuccess = true
            } catch (e: Exception) { }
        }
    }
}