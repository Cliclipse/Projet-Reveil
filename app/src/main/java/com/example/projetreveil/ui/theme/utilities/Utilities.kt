package com.example.projetreveil.ui.theme.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projetreveil.R


@Composable
fun Logo(size : Float){
    val imageModifier = Modifier.size(size.dp).clip(CircleShape)
    Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Crop,  modifier = imageModifier)

}

@Composable
fun HeadedTextField(text : String) : String {
    var textWriten : String = "";
    Text(text = text)
    TextField(value = textWriten, onValueChange = {textWriten = it})
    return textWriten
}