package com.example.projetreveil.ui.theme.screens

import android.Manifest
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SetAlarmPage(name: String,
                  context: Context,
                  modifier: Modifier = Modifier,
                  alarmsViewModel: AlarmsViewModel,
                  onConfirmClicked: () -> Unit,
                  onCancelClicked: () -> Unit){

    val vibrationOption: MutableState<Boolean> = remember { mutableStateOf(true) }

    val dailyOption: MutableState<Boolean> = remember { mutableStateOf(false) }


    val currentTime = remember { Calendar.getInstance() }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    val createAlarm = {
        alarmsViewModel.AddAlarm(hour = timePickerState.hour,
            minutes = timePickerState.minute,
            enabled = true,
            onAlarmCreated = {
                    idAlarm ->
                AlarmsUtilities().SetAlarm(
                    context = context,
                    alarmID = idAlarm,
                    hour = timePickerState.hour,
                    minutes = timePickerState.minute,
                    enabled = true,
                    vibration = vibrationOption.value,
                    daily = dailyOption.value
                )
            },
            vibration = vibrationOption.value,
            daily = dailyOption.value
        )

        onConfirmClicked()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()){
            isGranted ->
        if (isGranted){
            createAlarm()
        }
    }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, ) {
                        TopFlag(stringResource(R.string.perso))

            Spacer(Modifier.height(95.dp));

            TimeInput(timePickerState)

            Spacer(Modifier.height(60.dp));


            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start,) {

                Spacer(modifier = Modifier.height(6.dp))

                OptionToggle(stringResource(R.string.vibration) , vibrationOption)

                Spacer(modifier = Modifier.height(6.dp))

                OptionToggle(stringResource(R.string.daily) , dailyOption)
            }
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(horizontal = 50.dp, vertical = 12.dp).padding(bottom = 24.dp) ,  horizontalArrangement = Arrangement.SpaceBetween){
            FloatingActionButton(onClick = { onCancelClicked() }, containerColor = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(80.dp)) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.5f),
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.abandon),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            FloatingActionButton(
                onClick = {

                    if (Build.VERSION.SDK_INT >= 33) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(80.dp))
            {
                Icon(
                    modifier = Modifier.fillMaxSize(0.5f),
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.add) ,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}



@Composable
fun OptionToggle(name : String , option: MutableState<Boolean>){
    Row( modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, ) {
        //Spacer(Modifier.height(50.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = option.value,
            onCheckedChange = { option.value = !option.value },
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