package com.example.projetreveil.ui.theme.RoomDatas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minutes: Int,
    val enabled: Boolean
) {

}