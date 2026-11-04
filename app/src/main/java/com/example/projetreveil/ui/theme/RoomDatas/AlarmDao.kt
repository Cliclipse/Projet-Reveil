package com.example.projetreveil.ui.theme.RoomDatas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddAlarm(alarm: Alarm): Long

    @Delete
    suspend fun DeleteAlarm(alarm: Alarm)

    @Update
    suspend fun UpdateAlarm(alarm: Alarm)

    @Query("UPDATE alarms SET enabled = :enabled WHERE id = :alarmID")
    suspend fun UpdateAlarmEnabledWithID(alarmID: Int, enabled: Boolean): Int

    @Query("SELECT * from alarms ORDER BY hour, minutes")
    fun GetAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * from alarms WHERE enabled = 1 ORDER BY hour, minutes")
    fun GetAllEnabledAlarms(): Flow<List<Alarm>>

    @Query("SELECT * from alarms WHERE id = :alarmID")
    fun GetAlarmsWithID(alarmID: Int) : Flow<List<Alarm>>

    @Query("SELECT * FROM alarms")
    suspend fun GetAllAlarmsDebug(): List<Alarm>

}