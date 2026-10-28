package com.example.projetreveil.ui.theme.RoomDatas

import android.content.Context
import androidx.room.Room

class AlarmDatabaseProvider {

    @Volatile
    private var Instance: AlarmDatabase? = null

    fun GetDatabase(context: Context): AlarmDatabase{
        return Instance ?: synchronized(this){
            Instance ?: Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                "alarm_database"
            ).build().also { Instance = it }
        }
    }
}