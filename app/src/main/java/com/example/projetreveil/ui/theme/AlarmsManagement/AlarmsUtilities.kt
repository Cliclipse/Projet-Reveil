package com.example.projetreveil.ui.theme.AlarmsManagement

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projetreveil.MainActivity
import java.util.Calendar

class AlarmsUtilities {
    fun SetAlarm(context : Context, alarmID: Int, hour: Int, minutes: Int, enabled: Boolean){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarmID,
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("alarm-id", alarmID)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (!enabled){
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            return;
        }

        val triggerTime = NextOccurrence(hour, minutes)

        val showIntent = PendingIntent.getActivity(
            context,
            alarmID,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(triggerTime, showIntent),
            pendingIntent
        )
    }

    private fun NextOccurrence(hour: Int, minutes: Int): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis())
                add(Calendar.DAY_OF_YEAR, 1)
        }
        return calendar.timeInMillis
    }

    fun StopAlarm(context: Context, alarmID: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarmID,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.cancel(1)
    }
}