package com.example.projetreveil.ui.theme.AlarmsManagement

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.projetreveil.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val alarmID = intent.getIntExtra("alarm-id", -1)
        val vibration = intent.getBooleanExtra("alarm-vibration", true)
        val daily = intent.getBooleanExtra("alarm-daily", false)
        val hour = intent.getIntExtra("alarm-hour", -1)
        val minutes = intent.getIntExtra("alarm-minutes", -1)

        val targetChannelName = if (vibration) CHANNEL_ALARM_VIBRATION_NAME else CHANNEL_ALARM_NO_VIBRATION_NAME
        val targetChannelID = if (vibration) CHANNEL_ALARM_VIBRATION_ID else CHANNEL_ALARM_NO_VIBRATION_ID

        val channel = NotificationChannel(targetChannelID,
            targetChannelName,
            NotificationManager.IMPORTANCE_HIGH).apply {
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
            )
            enableVibration(vibration)
        }
        notificationManager.createNotificationChannel(channel)

        val openApp = PendingIntent.getActivity(
            context,
            0,
            Intent(context,
                MainActivity::class.java).apply {
                putExtra("open-alarm-ringing-page", true)
                putExtra("alarm-id", alarmID)
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, targetChannelID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Alarme en cours")
            .setContentText("L'alarme sonne !")
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(openApp)
            .setAutoCancel(true)
            .build()

        notification.flags = notification.flags or Notification.FLAG_INSISTENT

        notificationManager.notify(1, notification)

        if (daily && alarmID != -1 && hour != -1 && minutes != -1){

            AlarmsUtilities().SetAlarm(
                context = context,
                alarmID = alarmID,
                hour = hour,
                minutes = minutes,
                enabled = true,
                vibration = vibration,
                daily = daily
            )
        }

    }

    companion object {
        const val CHANNEL_ALARM_VIBRATION_ID = "alarm_vibration_channel"
        const val CHANNEL_ALARM_VIBRATION_NAME = "alarm_vibration"
        const val CHANNEL_ALARM_NO_VIBRATION_ID = "alarm_no_vibration_channel"
        const val CHANNEL_ALARM_NO_VIBRATION_NAME = "alarm_no_vibration"
    }
}