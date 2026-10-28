package com.example.projetreveil.ui.theme.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Long? = null,
    val GroupName : String,
    @SerialName("created_at") val heure: String,
)