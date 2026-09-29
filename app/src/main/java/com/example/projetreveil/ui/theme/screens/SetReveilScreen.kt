package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.projetreveil.ui.theme.utilities.Logo


data class OptionAlarme( //Je fais pas un tableau car pas tout ne sera pas que boolean par la suite
    val option1: Boolean,
    val option2: Boolean,
    val option3: Boolean
)


@Composable
fun SetReveilPage(name: String, modifier: Modifier = Modifier){
    var optionAlarme : OptionAlarme = OptionAlarme(false, false , false);
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {

        //Truc qui se déroule

        Spacer(Modifier.height(200.dp)); //Temporaire, pour occuper la place que prendra le truc qui roule

        Logo(100f)


        Spacer(modifier = Modifier.height(50.dp))

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start,) {

            //Truc qui se déroule

            Spacer(modifier = Modifier.height(6.dp))

            Option("Vibration" , optionAlarme)

            Spacer(modifier = Modifier.height(6.dp))

            Option("Autre" , optionAlarme)

            Spacer(modifier = Modifier.height(6.dp))

            Option("Autre" , optionAlarme)


        }

        Option("Vibration" , optionAlarme)

    }
}



@Composable
fun Option(name : String , option : OptionAlarme){
    Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, ) {
        Spacer(Modifier.size(50.dp))
        Text(
            text = name,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = option.option1,
            onCheckedChange = {}
            )


    }
}