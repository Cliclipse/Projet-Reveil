package com.example.projetreveil.ui.theme.data.Objet

import kotlinx.serialization.Serializable

@Serializable
data class GroupUsersJunction(
    val idUser: String? = null,
    val idGroup: Long? = null
    )