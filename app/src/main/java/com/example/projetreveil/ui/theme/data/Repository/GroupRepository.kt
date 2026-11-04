package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.Group
import com.example.projetreveil.ui.theme.data.Objet.GroupUsersJunction
import io.github.jan.supabase.postgrest.from

class GroupRepository {


    private val supabase = SupabaseClient.client

    private val junctionRepo = GroupUsersJunctionRepository()


    suspend fun get(id: Long): List<Group> {
        return supabase.from("Group").select{
            filter { eq("id", id)}
        }.decodeList<Group>()
    }


    suspend fun getFromUserId(userId: String): List<Group> {
        val junctions: List<GroupUsersJunction> = junctionRepo.getByUserID(userId) //Recup liste de jonctions
        val groupIds :List<Long> = junctions.map { it.idGroup as Long } //Recup liste d'id des groupes
        if (groupIds.isEmpty()) return emptyList()
        return supabase.from("Group").select {
                filter { isIn("id", groupIds) }
            }.decodeList<Group>()
        }


    suspend fun getByUserId(userId: String): List<GroupUsersJunction> {
        return supabase.from("GroupUsersJunctionTable").select {
            filter { eq("userId", userId) }
        }.decodeList<GroupUsersJunction>()
    }

    suspend fun add(group: Group) {
        supabase.from("Group").insert(group)
    }

    suspend fun addAndGetID(group: Group) : Long {
        var groupeCreated : Group = supabase.from("Group").insert(group) { select() }
            .decodeSingle<Group>()
        return groupeCreated.id ?: error("pas d'id au groupe inséré... force")
    }

    suspend fun delete(id: String) {
        supabase.from("Group").delete {
            filter { eq("id", id) }
        }
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateGroupName(id: Long, newGroupName: String) {
        supabase.from("Group").update(
            { set("GroupName", newGroupName) }
        ) {
            filter { eq("id", id) }
        }
    }
}