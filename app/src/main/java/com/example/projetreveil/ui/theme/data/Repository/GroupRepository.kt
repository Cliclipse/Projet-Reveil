package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.Group
import io.github.jan.supabase.postgrest.from

class GroupRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<Group> {
        return supabase.from("group").select().decodeList<Group>()
    }

    suspend fun add(group: Group) {
        supabase.from("group").insert(group)
    }

    suspend fun delete(id: Long) {
        supabase.from("group").delete {
            filter { eq("id", id) }
        }
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateGroupName(id: Long, newGroupName: String) {
        supabase.from("group").update(
            { set("GroupName", newGroupName) }
        ) {
            filter { eq("id", id) }
        }
    }
}