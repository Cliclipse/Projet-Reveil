package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.Users
import io.github.jan.supabase.postgrest.from

class UsersRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<Users> {
        return supabase.from("users").select().decodeList<Users>()
    }
    suspend fun add(users: Users) {
        supabase.from("users").insert(users)
    }

    suspend fun delete(id: Long) {
        supabase.from("users").delete {
            filter { eq("id", id) }
        }
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateName(id: Long, nouveauNom: String) {
        supabase.from("users").update(
            { set("name", nouveauNom) }
        ) {
            filter { eq("id", id) }
        }
    }
    suspend fun updatePP(id: Long, newprofilPicture: Long) {
        supabase.from("users").update(
            { set("profil_picture", newprofilPicture) }
        ) {
            filter { eq("id", id) }
        }
    }
}