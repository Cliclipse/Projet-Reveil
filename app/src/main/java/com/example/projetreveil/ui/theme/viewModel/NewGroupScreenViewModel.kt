package com.example.projetreveil.ui.theme.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.GroupRepository
import com.example.projetreveil.data.GroupUsersJunctionRepository
import com.example.projetreveil.ui.theme.data.Objet.Group
import com.example.projetreveil.ui.theme.data.Objet.GroupUsersJunction
import com.example.projetreveil.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewGroupScreenViewModel(application: Application) : AndroidViewModel(application) { //AndroidVM permet d'avoir un context

    var groupName: String by mutableStateOf("");

    val groupRepository: GroupRepository = GroupRepository()
    val groupUsersJunctionRepository: GroupUsersJunctionRepository = GroupUsersJunctionRepository()

    val supabase = SupabaseClient.client

    var selectedImageUri by mutableStateOf<Uri?>(null)
    private val appContext: Context = getApplication<Application>().applicationContext

    fun registerGroup() { // d'abord je crée le groupe, puis je récupère l'id attribuée automatiquement, puis j'ajoute dans la junc function
        var group: Group = Group(null, groupName)
        var idAttribuee: Long

        viewModelScope.launch {
            idAttribuee = groupRepository.addAndGetID(group)


            // Créa du groupe
            var groupUsersJunction: GroupUsersJunction = GroupUsersJunction(
                idUser = supabase.auth.currentUserOrNull()?.id,
                idGroup = idAttribuee
            )
            groupUsersJunctionRepository.add(groupUsersJunction)

            // Gestion photo de profil du groupe
            val imageTmp = selectedImageUri
            if (imageTmp != null) {
                val bytes = readBytesFromUri(imageTmp)
                if (bytes != null) {
                    try {
                        val path = uploadGroupAvatar(idAttribuee, bytes)
                        Log.d("AVATAR", "Upload OK : $path")
                    } catch (e: Exception) {
                        Log.e("AVATAR", "Upload échoué : ${e.message}", e)
                    }
                }
            }
        }
    }


    suspend fun readBytesFromUri(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            appContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        }
    }


    suspend fun uploadGroupAvatar(groupId: Long, bytes: ByteArray): String {
        val path = "$groupId/avatar.jpg"
        SupabaseClient.client.storage.from("GroupPictureBucket").upload(path, bytes) {
                upsert = true
            }

        return path
    }
}