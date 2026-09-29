package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.ui.theme.utilities.HeadedTextField
import com.example.projetreveil.ui.theme.utilities.Logo
import com.example.projetreveil.ui.theme.viewModel.RegisterScreenViewModel


@Composable
fun RegisterPage(name: String, modifier: Modifier = Modifier){
    var alignment : Modifier = Modifier.fillMaxWidth()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = alignment ){

       val viewModel : RegisterScreenViewModel = viewModel()

        val spacerModifier = Modifier.size(200.dp)

        val midSpacerModifier = Modifier.size(20.dp)

        Spacer(spacerModifier)

        Logo(70f)

        Spacer(Modifier.size(40.dp))

        HeadedTextField("Pseudo" , viewModel.pseudo,  {textInField : String -> viewModel.pseudo = textInField})

        Spacer(midSpacerModifier)

        HeadedTextField("Mot de Passe" , viewModel.mdp,  {textInField : String -> viewModel.mdp = textInField})

        Spacer(midSpacerModifier)

        Button({} ) {
            Text(text = "Valider")
        }
    }
}