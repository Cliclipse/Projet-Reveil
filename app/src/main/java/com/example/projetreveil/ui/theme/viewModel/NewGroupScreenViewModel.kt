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

    fun registerGroup(){ // d'abord je crée le groupe, puis je récupère l'id attribuée automatiquement, puis j'ajoute dans la junc function
        var group : Group = Group(0 , groupName)
        var idAttribuee : Long

        viewModelScope.launch {
            idAttribuee = groupRepository.addAndGetID(group)

            var groupUsersJunction : GroupUsersJunction = GroupUsersJunction(
                idPerson = supabase.auth.currentUserOrNull()?.id,
                idGroup = idAttribuee
            )
            groupUsersJunctionRepository.add(groupUsersJunction)
        }


    }
}