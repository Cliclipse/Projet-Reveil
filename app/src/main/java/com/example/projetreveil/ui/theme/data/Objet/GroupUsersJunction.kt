package com.example.projetreveil.ui.theme.data.Objet

import kotlinx.serialization.Serializable

@Serializable
data class GroupUsersJunction(
    val idPerson: Long? = null,
    val idGroup: Long? = null
    )