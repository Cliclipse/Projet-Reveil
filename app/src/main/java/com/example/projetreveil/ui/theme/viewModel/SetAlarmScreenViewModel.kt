package com.example.projetreveil.ui.theme.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SetAlarmScreenViewModel: ViewModel() {

    var vibrationOption: MutableState<Boolean> = mutableStateOf(true)
        private set

    var dailyOption: MutableState<Boolean> = mutableStateOf(false)

    fun updateVibrationOption(vibration: Boolean){
        vibrationOption.value = vibration
    }

    fun updateDailyOption(daily: Boolean){
        dailyOption.value = daily
    }
}