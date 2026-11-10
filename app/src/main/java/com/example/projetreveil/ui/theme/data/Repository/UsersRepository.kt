package com.example.projetreveil.data

import android.util.Log
import com.example.projetreveil.ui.theme.data.Objet.Users
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class UsersRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<Users> {
        return supabase.from("users").select().decodeList<Users>()
    }
    suspend fun add(users: Users) {
        supabase.from("users").insert(users)
    }

    suspend fun delete(id: String) {
        supabase.from("users").delete {
            filter { eq("id", id) }
        }
    }


    suspend fun getUserByPseudo(pseudo : String) : Users? {
        return supabase.from("users").select {
            filter { eq("name", pseudo) }
        }.decodeSingleOrNull<Users>()
    }
    suspend fun getPseudos(ids: List<String>): Map<String, String> {
        if (ids.isEmpty()) return emptyMap()
        return supabase.from("users").select {
            filter { isIn("id", ids) }
        }.decodeList<Users>()
            .mapNotNull { u -> u.id?.let { it to u.name } }
            .toMap()
    }

    suspend fun getCurrentUser() : Users? {
        val userId = supabase.auth.currentUserOrNull()?.id

        Log.d("UsersRepository", "USER ID = $userId")

        if (userId == null)
            return null

        return supabase.from("users").select{
            filter {
                eq("id", userId)
            }
        }.decodeSingleOrNull()
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateName(id: String, nouveauNom: String) {
        supabase.from("users").update(
            { set("name", nouveauNom) }
        ) {
            filter { eq("id", id) }
        }
    }
    suspend fun updatePP(id: String, newprofilPicture: Long) {
        supabase.from("users").update(
            { set("profil_picture", newprofilPicture) }
        ) {
            filter { eq("id", id) }
        }
    }
}