package com.example.projetreveil.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.ui.theme.RoomDatas.Alarm
import com.example.projetreveil.ui.theme.RoomDatas.AlarmDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlarmsViewModel(
    private val alarmDao: AlarmDao
) : ViewModel()
{
    val alarms: StateFlow<List<Alarm>> = alarmDao.GetAllAlarms()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun AddAlarm(hour: Int, minutes: Int, enabled: Boolean, onAlarmCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val idNewAlarm = alarmDao.AddAlarm(Alarm(hour = hour, minutes = minutes, enabled = enabled))

            onAlarmCreated(idNewAlarm.toInt())
        }
    }
    fun DeleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmDao.DeleteAlarm(alarm = alarm)
        }
    }

    fun UpdateAlarmEnabled(alarm: Alarm, enabled: Boolean){
        viewModelScope.launch {
            alarmDao.UpdateAlarm(alarm.copy(enabled = enabled))
        }
    }
}

class AlarmsViewModelFactory (
    private val alarmDao: AlarmDao
): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(AlarmsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AlarmsViewModel(alarmDao = alarmDao) as T
        }

        throw IllegalArgumentException("View model du mauvais type, ce view modelFactory n'attend que un AlarmsViewModel")
    }

}