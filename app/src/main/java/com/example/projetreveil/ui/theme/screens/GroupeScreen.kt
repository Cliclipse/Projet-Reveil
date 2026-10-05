package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projetreveil.R
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
            GroupChat()
        }

    }
}

@Composable
fun PickGroupSpace(modifier: Modifier){
    Column(modifier = modifier){
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.padding(horizontal = 6.dp).fillMaxWidth()) {
            val groupes = listOf("Groupe 1", "Groupe 2", "Groupe 3")
            items(groupes){
                groupe -> GroupButton()
                Spacer(modifier = Modifier.height(10.dp))

            }
            item{
                AddGroupButton()
            }
        }
    }
}



@Composable
fun HeadOfGroupChat(){
    var backGroundColor = Color(0xFFE8C689)
    Box(modifier = Modifier.background(color = backGroundColor).fillMaxWidth() ,    contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(40.dp))
            Logo(75f)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Nom du Groupe")
            Spacer(modifier = Modifier.height(20.dp))
        }
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.TopEnd).padding(start = 6.dp , top = 6.dp) ,
            content = { Icon(Icons.Filled.MoreVert , contentDescription = "Ajouter" , tint = Color.White) })
    }
}

@Composable
fun GroupChat(){//Ajo uter ensuite une rélle lazy list avec les photos prises
    Spacer(modifier = Modifier.height(15.dp))
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
        items(4){
            Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Fit,  alignment = Alignment.Center, modifier = Modifier.padding(horizontal = 5.dp))
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}










@Composable
fun GroupButton(){
    //Temporaire
    val imageModifier = Modifier.size(65.dp).clip(shape = RoundedCornerShape(15.dp))
    Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Crop,  modifier = imageModifier)
}

@Composable
fun AddGroupButton(){
    IconButton(onClick = {}, modifier = Modifier.padding(start = 6.dp , top = 6.dp).size(65.dp).clip(shape = RoundedCornerShape(15.dp)),
        content = { Icon(Icons.Filled.AddCircle , contentDescription = "Ajouter Groupe" , tint = Color.White)})
}