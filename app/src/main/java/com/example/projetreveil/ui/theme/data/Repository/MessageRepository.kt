package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.Message
import io.github.jan.supabase.postgrest.from

class MessageRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<Message> {
        return supabase.from("group").select().decodeList<Message>()
    }
    suspend fun add(message: Message) {
        supabase.from("ChatImages").insert(message)
    }

    suspend fun delete(id: Long) {
        supabase.from("ChatImages").delete {
            filter { eq("id", id) }
        }
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateContent(id: Long, newContent: Long) {
        supabase.from("ChatImages").update(
            { set("content", newContent) }
        ) {
            filter { eq("id", id) }
        }
    }

    //Je fais pas d'update inGroupeId ou Sender, un message aurait aps de raison de changer
    //Déjà que sur cette app là, pas de raison que le msg soit modifié
}