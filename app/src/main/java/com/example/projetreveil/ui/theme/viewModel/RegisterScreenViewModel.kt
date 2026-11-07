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
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.providers.builtin.Email


enum class ErrorType {
    INVALID_CREDENTIALS,
    USER_ALREADY_EXISTS,
    EMAIL_NOT_CONFIRMED,
    INVALID_PASSWORD,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

class RegisterScreenViewModel : ViewModel() {

    enum class AuthStateEnum { IDLE, LOADING, SUCCESS, ERROR }

    val repository = UsersRepository();
    var pseudo by mutableStateOf("")
        private set

    var mdp by mutableStateOf("")
        private set

    fun updatePseudo(inPseudo: String){
        pseudo = inPseudo
    }

    fun updatePassword(inPassword: String){
        mdp = inPassword
    }

    var state by mutableStateOf(AuthStateEnum.IDLE)

    var usersRepository = UsersRepository();

    fun Init() {
        pseudo = ""
        mdp = ""
    }

    val supabase = SupabaseClient.client

    var errorType by mutableStateOf<ErrorType?>(null)
        private set

    private fun getErrorType(e: Exception): ErrorType {
        val message = e.message?.lowercase() ?: ""

        return when {
            message.contains("invalid login credentials") ->
                ErrorType.INVALID_CREDENTIALS

            message.contains("already registered") ->
                ErrorType.USER_ALREADY_EXISTS

            message.contains("email not confirmed") ->
                ErrorType.EMAIL_NOT_CONFIRMED

            message.contains("password") || message.contains("password should be at least") ->
                ErrorType.INVALID_PASSWORD

            message.contains("network") || message.contains("unable to resolve host") ->
                ErrorType.NETWORK_ERROR

            else -> ErrorType.UNKNOWN_ERROR
        }
    }

    private fun pseudoToEmail(pseudo: String) =
        "${pseudo.trim().lowercase()}@monapp.app"

    fun registerUser() {

        if (this.state == AuthStateEnum.LOADING)
            return

        viewModelScope.launch {
            try {
                errorType = null

                //supabase.auth.signOut()
                state = AuthStateEnum.LOADING
                supabase.auth.signUpWith(Email) {
                    this.email = pseudoToEmail(pseudo)       //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp
                }

                val userId = supabase.auth.currentUserOrNull()?.id

                if (userId == null){
                    throw Exception("No utilisateur found just after adding one ! This is an error !")
                }

                var user : Users = Users(id = userId, name = pseudo, profilPicture = 0)

                usersRepository.add(user)

                state = AuthStateEnum.SUCCESS
            } catch (e: Exception) {
                state = AuthStateEnum.ERROR
                errorType = getErrorType(e)
                Log.e("Auth", "Inscription échouée : ${e.message}", e)
            }
        }
    }


    fun connectUser() {
        if (this.state == AuthStateEnum.LOADING)
            return

        viewModelScope.launch {
            try {
                errorType = null

                state = AuthStateEnum.LOADING

                val email = pseudoToEmail(pseudo)

                supabase.auth.signInWith(Email) {
                    this.email = email      //Pas moyen d'enregistrer avec un pseudo + mdp, dcp tranfo du pseudo en email factice jamais vu par l'user
                    this.password = mdp
                }
                state = AuthStateEnum.SUCCESS
            } catch (e: Exception) {
                state = AuthStateEnum.ERROR
                errorType = getErrorType(e)
                Log.e("Auth", "Connection échouée ${e.message}", e)
            }
        }
    }
}