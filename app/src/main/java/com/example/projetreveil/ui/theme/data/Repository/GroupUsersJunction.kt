package com.example.projetreveil.data

import io.github.jan.supabase.postgrest.from

class GroupUsersJunction {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<GroupUsersJunction> {
        return supabase.from("groupUsersJunctionTable").select().decodeList<GroupUsersJunction>()
    }

    suspend fun add( GUJ: GroupUsersJunction) {
        supabase.from("groupUsersJunctionTable").insert(GUJ)
    }

    suspend fun delete(id: Long) {
        supabase.from("groupUsersJunctionTable").delete {
            filter { eq("id", id) }
        }
    }
}