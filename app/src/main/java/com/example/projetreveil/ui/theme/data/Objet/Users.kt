package com.example.projetreveil.ui.theme.data.Objet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id: Long? = null,
    val name : String,
    @SerialName("profil_picture") val profilPicture: Long?
)