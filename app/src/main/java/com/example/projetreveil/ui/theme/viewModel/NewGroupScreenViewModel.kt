package com.example.projetreveil.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.GroupRepository
import com.example.projetreveil.data.GroupUsersJunctionRepository
import com.example.projetreveil.ui.theme.data.Objet.Group
import com.example.projetreveil.ui.theme.data.Objet.GroupUsersJunction
import com.example.projetreveil.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class NewGroupScreenViewModel : ViewModel() {

    var groupName : String = "";

    val groupRepository : GroupRepository = GroupRepository()
    val groupUsersJunctionRepository : GroupUsersJunctionRepository  = GroupUsersJunctionRepository()

    val supabase = SupabaseClient.client

    suspend fun registerGroup(){
        var group : Group = Group(0 , groupName)
        var groupUsersJunction : GroupUsersJunction = GroupUsersJunction(idPerson = supabase.auth.currentUserOrNull()?.id
        )


        viewModelScope.launch {
            groupRepository.add(group)

        }
    }
}