package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.GroupUsersJunction
import io.github.jan.supabase.postgrest.from

class GroupUsersJunctionRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<GroupUsersJunction> {
        return supabase.from("GroupUsersJunctionTable").select().decodeList<GroupUsersJunction>()
    }

    suspend fun add( GUJ: GroupUsersJunction) {
        supabase.from("GroupUsersJunctionTable").insert(GUJ)
    }

    suspend fun delete(id: Long) {
        supabase.from("GroupUsersJunctionTable").delete {
            filter { eq("id", id) }
        }
    }
}