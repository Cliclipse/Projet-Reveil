package com.example.projetreveil.ui.theme.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterScreenViewModel : ViewModel() {

    var pseudo by mutableStateOf("");
    var mdp by mutableStateOf("");

    fun Init(){
        pseudo = ""
        mdp = ""
   }



}