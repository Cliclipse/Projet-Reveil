package com.example.projetreveil.ui.theme.viewModel

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var groupName : String by mutableStateOf("");

    val groupRepository : GroupRepository = GroupRepository()
    val groupUsersJunctionRepository : GroupUsersJunctionRepository  = GroupUsersJunctionRepository()

    val supabase = SupabaseClient.client

    var selectedImageUri by mutableStateOf<Uri?>(null)




    fun registerGroup(){ // d'abord je crée le groupe, puis je récupère l'id attribuée automatiquement, puis j'ajoute dans la junc function
        var group : Group = Group(null , groupName)
        var idAttribuee : Long

        viewModelScope.launch {
            idAttribuee = groupRepository.addAndGetID(group)

            var groupUsersJunction : GroupUsersJunction = GroupUsersJunction(
                idUser = supabase.auth.currentUserOrNull()?.id,
                idGroup = idAttribuee
            )
            groupUsersJunctionRepository.add(groupUsersJunction)
        }
    }
}