package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.projetreveil.ui.theme.utilities.Logo

@Composable
fun SetGroupePage(name : String){
    var backGroundColorLeft = Color(0xFF109C74)
    var backGroundColorRight = Color(0xFFF7ECAD)

    Row(){
        PickGroupSpace(modifier = Modifier.background(backGroundColorLeft).weight(0.25f).fillMaxSize())
        GroupeSpace(modifier = Modifier.background(backGroundColorRight).weight(0.75f).fillMaxSize())
    }
}


@Composable
fun GroupeSpace(modifier: Modifier){
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadOfGroupChat()
        }

    }
}

@Composable
fun PickGroupSpace(modifier: Modifier){
    Box(modifier = modifier) {

    }
}



@Composable
fun HeadOfGroupChat(){
    Box(modifier = Modifier.background(color = Color.Red).fillMaxWidth() ,    contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(40.dp))
            Logo(75f)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Nom du Groupe")
            Spacer(modifier = Modifier.height(40.dp))

        }

    }

}