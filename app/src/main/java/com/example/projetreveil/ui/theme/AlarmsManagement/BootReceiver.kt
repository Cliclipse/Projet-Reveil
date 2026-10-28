package com.example.projetreveil.ui.theme.AlarmsManagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import com.example.projetreveil.ui.theme.RoomDatas.Alarm
import com.example.projetreveil.ui.theme.RoomDatas.AlarmDatabase
import com.example.projetreveil.ui.theme.RoomDatas.AlarmDatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action != Intent.ACTION_BOOT_COMPLETED)
            return;

        val pendingResult = goAsync()
        val appContext = context.applicationContext

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        scope.launch {
            try{
                val alarmDatabase = AlarmDatabaseProvider().GetDatabase(context);

                val alarms = alarmDatabase.AlarmDao().GetAllEnabledAlarms().first()

                val alarmUtilities = AlarmsUtilities()

                for (alarm in alarms){
                    alarmUtilities.SetAlarm(appContext,
                        alarm.id,
                        alarm.hour,
                        alarm.minutes,
                        alarm.enabled
                    )
                }
            }
            catch (e: Exception){
                Log.e(
                    "Boot Receiver",
                    "Error when trying to restore alarm after shut down",
                    e
                    )
            }
        }
    }

}