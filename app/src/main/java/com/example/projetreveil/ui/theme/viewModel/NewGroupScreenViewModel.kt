package com.example.projetreveil.ui.theme.viewModel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.io.ByteArrayOutputStream

class NewGroupScreenViewModel(application: Application) : AndroidViewModel(application) { //AndroidVM permet d'avoir un context

    var groupName: String by mutableStateOf("");

    val groupRepository: GroupRepository = GroupRepository()
    val groupUsersJunctionRepository: GroupUsersJunctionRepository = GroupUsersJunctionRepository()

    val supabase = SupabaseClient.client

    var selectedImageUri by mutableStateOf<Uri?>(null)
    private val appContext: Context = getApplication<Application>().applicationContext

    fun registerGroup(onConfirmClicked: (Unit) -> Unit) {
        val group = Group(null, groupName, null)

        viewModelScope.launch {
            try {
                val idAttribuee = groupRepository.addAndGetID(group)
                groupUsersJunctionRepository.add(
                    GroupUsersJunction(
                        idUser = supabase.auth.currentUserOrNull()?.id,
                        idGroup = idAttribuee
                    )
                )
                val uri = selectedImageUri
                if (uri != null) {
                    val bytes = squareBytesFromUri(appContext, uri)
                    if (bytes != null) {
                        val path = uploadGroupAvatar(idAttribuee, bytes)
                        groupRepository.updatePictureLink(idAttribuee, path)
                    }
                }
            } catch (e: Exception) {
            }
            onConfirmClicked(Unit)
        }
    }
    
    suspend fun squareBytesFromUri(context: Context, uri: Uri): ByteArray? =
        withContext(Dispatchers.IO) {
            val bitmap = context.contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it)
            } ?: return@withContext null

            val side = minOf(bitmap.width, bitmap.height)
            val x = (bitmap.width - side) / 2
            val y = (bitmap.height - side) / 2
            val square = Bitmap.createBitmap(bitmap, x, y, side, side)

            ByteArrayOutputStream().use { out ->
                square.compress(Bitmap.CompressFormat.JPEG, 85, out)
                out.toByteArray()
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