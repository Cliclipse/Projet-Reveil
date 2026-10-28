package com.example.projetreveil.ui.theme.data.Objet

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Long? = null,
    val name : String,
    val content : Long?,
    val inGroupId : Long?,
    val sender : Long?,
    )