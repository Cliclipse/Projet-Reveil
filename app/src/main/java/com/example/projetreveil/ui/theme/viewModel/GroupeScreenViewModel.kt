package com.example.projetreveil.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.GroupRepository
import com.example.projetreveil.data.SupabaseClient
import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Group
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupeScreenViewModel()  : ViewModel() {

    val repository = GroupRepository();

    val supabase = SupabaseClient.client
    var userId by mutableStateOf<String?>(null)

    var groupes: List<Group>? = null

    init {
        viewModelScope.launch {
            try {
                supabase.auth.awaitInitialization()
                userId = supabase.auth.currentUserOrNull()?.id
                val id = userId ?: return@launch
                groupes = repository.get(id)
            } catch (e: Exception) {
                Log.e("Groupes", "Chargement échoué", e)
            }
        }
    }

    /*
    val groupes: StateFlow<List<Group>> = repository.getGroupesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    */
}