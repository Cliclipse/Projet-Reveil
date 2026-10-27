package com.example.projetreveil.ui.theme.screens

import android.content.Context
import android.icu.util.Calendar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.ui.Alignment
import com.example.projetreveil.ui.theme.utilities.TopFlag

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel


data class OptionAlarme( //Je fais pas un tableau car pas tout ne sera pas que boolean par la suite
    val option1: Boolean,
    val option2: Boolean,
    val option3: Boolean
)


@Composable
@OptIn(ExperimentalMaterial3Api::class) //evite l'affichage d'une erreur inutile sur l'ide
fun SetReveilPage(name: String,
                  context: Context,
                  modifier: Modifier = Modifier,
                  alarmsViewModel: AlarmsViewModel,
                  onConfirmClicked: () -> Unit,
                  onCancelClicked: () -> Unit){
    var optionAlarme : OptionAlarme = OptionAlarme(false, false , false);

    val currentTime = remember { Calendar.getInstance() }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, ) {
                        TopFlag(stringResource(R.string.perso))

            Spacer(Modifier.height(95.dp));

            TimeInput(timePickerState)

            Spacer(Modifier.height(60.dp));


            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start,) {

                Spacer(modifier = Modifier.height(6.dp))

                Option(stringResource(R.string.vibration) , optionAlarme)

                Spacer(modifier = Modifier.height(6.dp))

                Option(stringResource(R.string.repeat) , optionAlarme)

                Spacer(modifier = Modifier.height(6.dp))

                Option(stringResource(R.string.daily) , optionAlarme)
            }
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(horizontal = 50.dp, vertical = 12.dp).padding(bottom = 24.dp) ,  horizontalArrangement = Arrangement.SpaceBetween){
            FloatingActionButton(onClick = { onCancelClicked() }, containerColor = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(80.dp)) {
                Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.abandon), tint = MaterialTheme.colorScheme.onSecondary)
            }

            FloatingActionButton(
                onClick = {
                    alarmsViewModel.AddAlarm(hour = timePickerState.hour,
                        minutes = timePickerState.minute,
                        enabled = true
                    )

                    val alarmsUtilities: AlarmsUtilities = AlarmsUtilities()

                    alarmsUtilities.SetAlarm(
                        context = context,
                        hour = timePickerState.hour,
                        minutes = timePickerState.minute,
                        enabled = true
                        )

                    onConfirmClicked()
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(80.dp))
            {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add) ,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
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
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = option.option1,
            onCheckedChange = {},
            colors = androidx.compose.material3.SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                checkedBorderColor = MaterialTheme.colorScheme.tertiary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = MaterialTheme.colorScheme.outline)
        )
    }
}