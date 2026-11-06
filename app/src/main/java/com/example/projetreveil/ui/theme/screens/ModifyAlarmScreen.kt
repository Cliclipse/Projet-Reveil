package com.example.projetreveil.ui.theme.screens

import android.Manifest
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.RoomDatas.Alarm
import com.example.projetreveil.ui.theme.utilities.ConfirmActionPopup
import com.example.projetreveil.ui.theme.utilities.LoadingIndicator
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel
import com.example.projetreveil.ui.theme.viewModel.SetAlarmScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyAlarmPage(name: String,
                    context: Context,
                    modifier: Modifier = Modifier,
                    associatedAlarmID: Int,
                    alarmsViewModel: AlarmsViewModel,
                    onConfirmClicked: () -> Unit,
                    onCancelClicked: () -> Unit
){
    val setAlarmScreenViewModel: SetAlarmScreenViewModel = viewModel()

    val associatedAlarms by alarmsViewModel.GetAlarmsWithID(alarmID = associatedAlarmID).collectAsStateWithLifecycle()

    val alarm = associatedAlarms.firstOrNull()

    alarmsViewModel.DebugAlarms()

    if (alarm == null) {
        LoadingIndicator()
        return
    }

    LaunchedEffect(alarm.id) {
        setAlarmScreenViewModel.updateVibrationOption(alarm.vibration)
        setAlarmScreenViewModel.updateDailyOption(alarm.daily)
    }

    val vibration = setAlarmScreenViewModel.vibrationOption
    val daily = setAlarmScreenViewModel.dailyOption

    key(alarm.id) {
        val timePickerState = rememberTimePickerState(
            initialHour = alarm.hour,
            initialMinute = alarm.minutes,
            is24Hour = true
        )

        val modifyAlarm = {
            alarmsViewModel.UpdateAlarm(
                alarm = Alarm(
                    id = alarm.id,
                    hour = timePickerState.hour,
                    minutes = timePickerState.minute,
                    enabled = alarm.enabled,
                    vibration = vibration.value,
                    daily = daily.value
                )
            )

            if (alarm.enabled){
                AlarmsUtilities().SetAlarm(
                    context = context,
                    alarmID = alarm.id,
                    hour = timePickerState.hour,
                    minutes = timePickerState.minute,
                    enabled = true,
                    vibration = setAlarmScreenViewModel.vibrationOption.value,
                    daily = setAlarmScreenViewModel.dailyOption.value
                )
            }

            onConfirmClicked()
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                modifyAlarm()
            }
        }

        var isDisplayingConfirmModifyAlarmPopup: Boolean by rememberSaveable { mutableStateOf(false) }

        ModifyAlarmContent(
            modifier = modifier,
            timePickerState = timePickerState,
            vibrationValue = setAlarmScreenViewModel.vibrationOption.value,
            dailyOptionValue = setAlarmScreenViewModel.dailyOption.value,
            onVibrationToggled = {
                setAlarmScreenViewModel.updateVibrationOption(it)
            },
            onDailyToggled = {
                setAlarmScreenViewModel.updateDailyOption(it)
            },
            onConfirmClicked = {
                isDisplayingConfirmModifyAlarmPopup = true
            },
            onCancelClicked = {
                onCancelClicked()
            }
        )

        ConfirmActionPopup(
            display = isDisplayingConfirmModifyAlarmPopup,
            onDismiss = {
                isDisplayingConfirmModifyAlarmPopup = false
            },
            onConfirm = {
                if (Build.VERSION.SDK_INT >= 33) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                isDisplayingConfirmModifyAlarmPopup = false
            },
            title = stringResource(R.string.modify_alarm_title),
            text = stringResource(R.string.modify_alarm_text)
        )
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyAlarmContent(
    modifier: Modifier = Modifier,
    timePickerState: TimePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
        is24Hour = true
    ),
    vibrationValue: Boolean = true,
    dailyOptionValue: Boolean = false,
    onVibrationToggled: (Boolean) -> Unit = {},
    onDailyToggled: (Boolean) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onConfirmClicked: () -> Unit = {}
){
    SetAlarmContent(
        modifier = modifier,
        timePickerState = timePickerState,
        vibrationValue = vibrationValue,
        dailyOptionValue = dailyOptionValue,
        onVibrationToggled = {
            onVibrationToggled(it)
        },
        onDailyToggled = {
            onDailyToggled(it)
        },
        onConfirmClicked = {
            onConfirmClicked()
        },
        onCancelClicked = {
            onCancelClicked()
        }
    )
}