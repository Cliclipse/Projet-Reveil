package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projetreveil.R


@Composable
fun RegisterTextField(text : String) : String {
    var textWriten : String = "";
    Text(text = text)
    TextField(value = textWriten, onValueChange = {textWriten = it})
    return textWriten
}

@Composable
fun Logo(size : Float){
    val imageModifier = Modifier.size(size.dp).clip(CircleShape)
    Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Crop,  modifier = imageModifier)

}

@Composable
fun RegisterPage(name: String, modifier: Modifier = Modifier){
    var mdp : String = "";
    var pseudo : String = "";
    var alignment : Modifier = Modifier.fillMaxWidth()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = alignment ){
        //Spacer()
        Logo(45f)
        RegisterTextField("Pseudo")

        RegisterTextField("Mot de Passe")

        Button({} ) {
            Text(text = "Valider")
        }
    }
}