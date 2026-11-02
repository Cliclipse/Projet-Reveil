package com.example.projetreveil.data

import io.github.jan.supabase.postgrest.from

class GroupUsersJunctionRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<GroupUsersJunctionRepository> {
        return supabase.from("groupUsersJunctionTable").select().decodeList<GroupUsersJunctionRepository>()
    }

    suspend fun add( GUJ: GroupUsersJunctionRepository) {
        supabase.from("groupUsersJunctionTable").insert(GUJ)
    }

    suspend fun delete(id: Long) {
        supabase.from("groupUsersJunctionTable").delete {
            filter { eq("id", id) }
        }
    }
}