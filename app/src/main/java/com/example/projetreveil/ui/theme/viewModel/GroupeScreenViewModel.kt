package com.example.projetreveil.ui.theme.viewModel

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.GroupRepository
import com.example.projetreveil.data.GroupUsersJunctionRepository
import com.example.projetreveil.data.MessageRepository
import com.example.projetreveil.data.SupabaseClient
import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Group
import com.example.projetreveil.ui.theme.data.Objet.GroupUsersJunction
import com.example.projetreveil.ui.theme.data.Objet.Message
import com.example.projetreveil.ui.theme.data.Objet.Users
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Instant

class GroupeScreenViewModel()  : ViewModel() {



    // Repos
    val groupeRepository = GroupRepository();
    val userRespository = UsersRepository();
    val GroupUsersJunctionRepository = GroupUsersJunctionRepository()
    val messageRepository = MessageRepository()

    //Variable d'accès à la bdd. Probablement pas ouf de les avoir là, mais faudrait enregistrer l'id de l'user en local à un moment. Pour le moment ça fait alrgement le taff
    val supabase = SupabaseClient.client
    var userId by mutableStateOf<String?>(null)

    //Accès au groupe actuellement shown
    var groupShownId by mutableStateOf<Long?>(null)
    var groupShown by mutableStateOf<Group?>(null)
    var GroupIcon by mutableStateOf<ImageBitmap?>(null)
    var lastMessages by mutableStateOf<List<Message>>(emptyList())


    // Variables pour le dialog d'ajout d'user
    var showAddMembrerDialog by mutableStateOf(false)
    var memberNotFound by mutableStateOf(false)



    // Alors je crois que je suis forcé de faire un truc bizarre ainsi, pas encore tout compris au flow
    //mais on modifie _groupes et on lit groupes
    private val _groupes = MutableStateFlow<List<Group>>(emptyList())
    var groupes : StateFlow<List<Group>> = _groupes

    init{
        loadGroups()
}

    fun loadGroups(){
        viewModelScope.launch{
            try {
                supabase.auth.awaitInitialization()
                userId = supabase.auth.currentUserOrNull()?.id
                val id = userId ?: return@launch
                _groupes.value = groupeRepository.getFromUserId(id)


            }
            catch (e: Exception) { Log.e("Groupes", "Chargement échoué", e) }
        }
    }

    fun ChangeGroupShown(idGroup : Long?){
        if (idGroup == groupShownId || idGroup == null) return
        groupShownId = idGroup
        viewModelScope.launch {
            val id = groupShownId ?: return@launch
            groupShown = groupeRepository.get(id)

            GroupIcon = groupeRepository.downloadGroupAvatar(groupShown?.GroupPictureLink)
            lastMessages =  messageRepository.getLastMessages(id)
        }
    }

    fun OpenAddFriendDialog(){
        showAddMembrerDialog = true
    }

    fun CloseAddFriendDialog(){
        showAddMembrerDialog = false
        memberNotFound = false

    }

    fun onConfirm(pseudo : String) {
        viewModelScope.launch{
            val friendToAdd : Users? = userRespository.getUserByPseudo(pseudo)
            if (friendToAdd == null){
                memberNotFound = true
                return@launch
            }
            val groupUsersJunction = GroupUsersJunction(friendToAdd.id, groupShownId)
            GroupUsersJunctionRepository.add(groupUsersJunction)
            CloseAddFriendDialog()
        }
    }



    fun SendAwokenMsg(){
        val msg = Message(null, groupShownId , userId , null)
        viewModelScope.launch{
            messageRepository.add(msg)
        }
    }
}