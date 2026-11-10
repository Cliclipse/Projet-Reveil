package com.example.projetreveil.ui.theme.data.Objet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Message(
    val id: Long? = null,
    val inGroupId : Long?,
    val sender : String?,
    @SerialName("created_at") val createdAt: Instant? = null
    )


