package com.example.projetreveil.ui.theme.RoomDatas

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun AlarmDao(): AlarmDao
}