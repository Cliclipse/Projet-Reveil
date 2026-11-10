package com.example.projetreveil.data

import com.example.projetreveil.ui.theme.data.Objet.Message
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class MessageRepository {

    private val supabase = SupabaseClient.client

    suspend fun get(): List<Message> {
        return supabase.from("Chat").select().decodeList<Message>()
    }
    suspend fun add(message: Message) {
        supabase.from("Chat").insert(message)
    }

    suspend fun delete(id: Long) {
        supabase.from("Chat").delete {
            filter { eq("id", id) }
        }
    }

    // Fct d'updates pr modif un champ d'un élément

    suspend fun updateContent(id: Long, newContent: Long) {
        supabase.from("Chat").update(
            { set("content", newContent) }
        ) {
            filter { eq("id", id) }
        }
    }

    //Prend les 30 derniers msgs d'un groupe
    suspend fun getLastMessages(groupId: Long): List<Message> =
        supabase.from("Chat").select {
            filter { eq("inGroupId", groupId) }
            order("created_at", Order.DESCENDING)
            limit(30)
        }.decodeList<Message>()



    //Je fais pas d'update inGroupeId ou Sender, un message aurait aps de raison de changer
    //Déjà que sur cette app là, pas de raison que le msg soit modifié
}