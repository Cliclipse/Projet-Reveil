package com.example.projetreveil.ui.theme.screens

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.ui.Alignment
import com.example.projetreveil.ui.theme.utilities.TopFlag
import java.time.LocalTime

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds


data class OptionAlarme( //Je fais pas un tableau car pas tout ne sera pas que boolean par la suite
    val option1: Boolean,
    val option2: Boolean,
    val option3: Boolean
)


@Composable
@OptIn(ExperimentalMaterial3Api::class) //evite l'affichage d'une erreur inutile sur l'ide
fun SetReveilPage(name: String, modifier: Modifier = Modifier,
                  onConfirmClicked: () -> Unit,
                  onCancelClicked: () -> Unit){
    var optionAlarme : OptionAlarme = OptionAlarme(false, false , false);
    var time by remember {
        mutableStateOf(Calendar.getInstance())
    }

    LaunchedEffect(Unit) {
        while (true){
            time = Calendar.getInstance()
            delay(1000.milliseconds)
        }
    }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, ) {
                        TopFlag("PERSO")

            Spacer(Modifier.height(95.dp)); //Temporaire, pour occuper la place que prendra le truc qui roule

            //Truc qui se déroule

            var state : TimePickerState = TimePickerState(time.get(java.util.Calendar.HOUR), time.get(java.util.Calendar.MINUTE) , true );
            TimeInput(state)


            Spacer(Modifier.height(60.dp)); //Temporaire, pour occuper la place que prendra le truc qui roule


            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start,) {

                //Truc qui se déroule

                Spacer(modifier = Modifier.height(6.dp))

                Option("Vibration" , optionAlarme)

                Spacer(modifier = Modifier.height(6.dp))

                Option("Répéter" , optionAlarme)

                Spacer(modifier = Modifier.height(6.dp))

                Option("Quotidien" , optionAlarme)
            }
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(horizontal = 50.dp, vertical = 12.dp).padding(bottom = 24.dp) ,  horizontalArrangement = Arrangement.SpaceBetween){
            FloatingActionButton(onClick = { onCancelClicked() }, containerColor = Color.Red, modifier = Modifier.size(80.dp)) {
                Icon(Icons.Filled.Close, contentDescription = "Annuler", tint = Color.White,)
            }

            FloatingActionButton(onClick = { onConfirmClicked() }, containerColor = Color.Green, modifier = Modifier.size(80.dp)) {
                Icon(Icons.Filled.Add, contentDescription = "Ajouter" , tint = Color.White)
            }
        }


    }




}



@Composable
fun Option(name : String , option : OptionAlarme){ //pas fonctionnel du tout sur la logique
    Row( modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, ) {
        //Spacer(Modifier.height(50.dp))
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