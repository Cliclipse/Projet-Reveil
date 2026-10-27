package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.utilities.HeadedTextField
import com.example.projetreveil.ui.theme.utilities.Logo
import com.example.projetreveil.ui.theme.viewModel.RegisterScreenViewModel


@Composable
fun RegisterPage(name: String, modifier: Modifier = Modifier, onConfirmClicked: (Unit) -> Unit){
    var alignment : Modifier = Modifier.fillMaxWidth()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = alignment ){

       val viewModel : RegisterScreenViewModel = viewModel()

        val spacerModifier = Modifier.size(200.dp)

        val midSpacerModifier = Modifier.size(20.dp)

        Spacer(spacerModifier)

        Logo(70f)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = stringResource(R.string.login), color = MaterialTheme.colorScheme.onBackground, fontSize = 24.sp)

        Spacer(Modifier.size(40.dp))

        HeadedTextField(stringResource(R.string.pseudo) , viewModel.pseudo,  {textInField : String -> viewModel.pseudo = textInField})

        Spacer(midSpacerModifier)

        HeadedTextField(stringResource(R.string.password) , viewModel.mdp,  {textInField : String -> viewModel.mdp = textInField})

        Spacer(midSpacerModifier)

        Button(
            colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary),
            onClick = {
            if (viewModel.pseudo != "" && viewModel.mdp != ""){
                onConfirmClicked(Unit)
            }
        }) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}