package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.utilities.HeadedTextField
import com.example.projetreveil.ui.theme.utilities.Logo
import com.example.projetreveil.ui.theme.viewModel.GroupeScreenViewModel
import com.example.projetreveil.ui.theme.viewModel.NewGroupScreenViewModel

@Composable
fun NewGroupScreen(name : String){
    val viewModel : NewGroupScreenViewModel = viewModel()

    Column(modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
        val spacerModifier = Modifier.size(200.dp)
        val midSpacerModifier = Modifier.size(20.dp)

        Spacer(spacerModifier)

        Logo(70f)
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(midSpacerModifier)

        Text(text = stringResource(R.string.new_group), color = MaterialTheme.colorScheme.onBackground, fontSize = 24.sp)

        HeadedTextField(stringResource(R.string.group_name) , viewModel.pseudo,  {textInField : String -> viewModel.pseudo = textInField})
        Spacer(midSpacerModifier)

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary),
            onClick = {
                if (viewModel.groupName != ""){
                    viewModel.registerGroup()
                }
            }) {
            Text(text = stringResource(R.string.confirm))
        }


    }

}