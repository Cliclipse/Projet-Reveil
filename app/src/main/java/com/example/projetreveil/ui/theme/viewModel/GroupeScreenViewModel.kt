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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupeScreenViewModel()  : ViewModel() {

    val repository = GroupRepository();

    val supabase = SupabaseClient.client
    var userId by mutableStateOf<String?>(null)


    var groupShown : Long? = null

    // Alors je crois que je suis forcé de faire un truc bizarre ainsi, pas encore tout compris au flow
    //mais on modifie _groupes et on lit groupes
    private val _groupes = MutableStateFlow<List<Group>>(emptyList())
    var groupes : StateFlow<List<Group>> = _groupes

    init{
        viewModelScope.launch{
            try {
                supabase.auth.awaitInitialization()
                userId = supabase.auth.currentUserOrNull()?.id
                val id = userId ?: return@launch
                _groupes.value = repository.getFromUserId(id)
            }
            catch (e: Exception) { Log.e("Groupes", "Chargement échoué", e) }
            Log.e("Groupes", groupes.value.size.toString())
        }
}


    fun ChangeGroupShown(idGroup : Long?){
        if (idGroup == groupShown) return

        Log.e("Groupes", idGroup.toString())


    }

    /*
    val groupes: StateFlow<List<Group>> = repository.getGroupesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    */
}