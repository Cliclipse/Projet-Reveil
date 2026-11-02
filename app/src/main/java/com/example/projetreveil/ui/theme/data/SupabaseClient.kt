package com.example.projetreveil.data

import com.example.projetreveil.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient { //object ca fait un singleton. Beosin pour accéder à la table auth de la bdd et avoir l'id de l'utilisateur actuel sur tous les écrans
    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }

}