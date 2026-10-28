package com.example.projetreveil.ui.theme.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Personne(
    val id: Long? = null,
    val name : String,
    @SerialName("created_at") val heure: String,
    @SerialName("profil_picture") val profilPicture: Long?
)