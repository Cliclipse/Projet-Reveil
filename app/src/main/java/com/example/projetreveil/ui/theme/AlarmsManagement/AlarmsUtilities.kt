package com.example.projetreveil.ui.theme.AlarmsManagement

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmsUtilities {
    val alarmToIDMap: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    var currentIdCount: Int = 0

    fun SetAlarm(context : Context, hour: Int, minutes: Int, enabled: Boolean){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (enabled){
            // Enable
            EnableAlarm(context = context,
                alarmManager = alarmManager,
                hour = hour,
                minutes = minutes
            )
        }
        else{
            // Disable
            DisableAlarm(
                context = context,
                alarmManager = alarmManager,
                hour = hour,
                minutes = minutes
            )
        }
    }

    fun EnableAlarm(context: Context, alarmManager: AlarmManager, hour: Int, minutes: Int){

        val showIntent = PendingIntent.getBroadcast(
            context, currentIdCount, Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val operationIntent = PendingIntent.getBroadcast(
            context, currentIdCount, Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {

            alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(
                NextOccurrence(hour = hour, minutes = minutes),
                showIntent),
                operationIntent)

            ++currentIdCount
        }
        catch (e: SecurityException){
            // TODO: mettre une popup d'echec car l'autorisation n'est pas là
        }
    }

    fun DisableAlarm(context: Context, alarmManager: AlarmManager, hour: Int, minutes: Int){

        var alarmID: Int = -1

        val hourMinutesPair: Pair<Int, Int> = Pair(hour, minutes)

        if (alarmToIDMap.containsKey(hourMinutesPair))
            return;

        alarmID = alarmToIDMap.getValue(hourMinutesPair)

        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmID,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

    }

    private fun NextOccurrence(hour: Int, minutes: Int): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
        }
        return cal.timeInMillis
    }
}