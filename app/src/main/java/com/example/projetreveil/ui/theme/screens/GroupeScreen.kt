package com.example.projetreveil.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projetreveil.R
import com.example.projetreveil.data.SupabaseClient
import com.example.projetreveil.ui.theme.data.Objet.Group
import com.example.projetreveil.ui.theme.utilities.Logo
import com.example.projetreveil.ui.theme.utilities.RadialMenu
import com.example.projetreveil.ui.theme.viewModel.GroupeScreenViewModel
import io.github.jan.supabase.storage.storage

@Composable
fun SetGroupePage(modifier: Modifier = Modifier,
    onAddGroup : () ->Unit,
                 onGoToMenuclicked: () -> Unit,
                 onGoToConfigureAlarmClicked: () -> Unit,
                 onGoToSettingsClicked: () -> Unit)
{
    val viewModel: GroupeScreenViewModel = viewModel()
    LaunchedEffect(Unit) { viewModel.loadGroups() }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            PickGroupSpace(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(0.25f)
                    .fillMaxSize(),
                viewModel,
                onAddGroup
            )
            GroupeSpace(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .weight(0.75f)
                    .fillMaxSize(),
                viewModel
            )
        }
        // Radial Menu bottom right
        RadialMenu(
            modifier = Modifier
                .fillMaxSize()
                .padding(34f.dp),
            isInMenu = false,
            onGoToMenuOrGroupClicked = { onGoToMenuclicked() },
            onGoToConfigureAlarmClicked = { onGoToConfigureAlarmClicked() },
            onGoToSettingsClicked = { onGoToSettingsClicked() }
        )
    }
}



    @Composable
fun GroupeSpace(modifier: Modifier , vm : GroupeScreenViewModel ){
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadOfGroupChat(vm)
            GroupChat(vm)
        }
    }
}

@Composable
fun PickGroupSpace(modifier: Modifier , vm : GroupeScreenViewModel, onAddGroup : () ->Unit ){
    val groupesList by vm.groupes.collectAsState()
    Column(modifier = modifier){
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth()) {
            items(groupesList) { groupe ->
                GroupButton(vm, groupe)
                Spacer(modifier = Modifier.height(10.dp))
            }
            item{
                AddGroupButton(onAddGroup, vm)
            }
        }
    }
}



@Composable
fun HeadOfGroupChat(vm : GroupeScreenViewModel ){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.surface)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            var imageModifier = Modifier.size(75.dp).clip(CircleShape)
            val icon = vm.GroupIcon

            Spacer(modifier = Modifier.height(20.dp))

            if (icon != null) {
                Image(
                    bitmap = icon,
                    contentDescription = "Photo du groupe",
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.penguin),
                    contentDescription = "LogoPenguin",
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text((vm.groupShown?.GroupName ?: stringResource(R.string.group_name)),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
        IconButton(onClick = {vm.OpenAddFriendDialog()}, modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(start = 6.dp, top = 6.dp) ,
            content = { Icon(Icons.Filled.Add , contentDescription = "Ajouter" , tint = MaterialTheme.colorScheme.onSurfaceVariant) })
    }
}

@Composable
fun GroupChat(vm : GroupeScreenViewModel ){//Ajo uter ensuite une rélle lazy list avec les photos prises
    Spacer(modifier = Modifier.height(15.dp))
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
        items(4){
            Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Fit,  alignment = Alignment.Center, modifier = Modifier.padding(horizontal = 5.dp))
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun GroupButton(vm: GroupeScreenViewModel, groupe: Group) {
    val url = remember(groupe.GroupPictureLink) {
        groupe.GroupPictureLink?.let {
            SupabaseClient.client.storage.from("GroupPictureBucket").publicUrl(it)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(15.dp))
            .size(65.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Log.d("AVATAR", "url liste = $url")

        AsyncImage(
            model = url,
            contentDescription = "Photo du groupe",
            placeholder = painterResource(R.drawable.penguin),
            error = painterResource(R.drawable.penguin),
            fallback = painterResource(R.drawable.penguin),
            onError = { Log.e("AVATAR", "Coil erreur : ${it.result.throwable.message}") },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable { vm.ChangeGroupShown(groupe.id) }
        )
    }
}

@Composable
fun AddGroupButton( onAddGroup : () ->Unit, vm : GroupeScreenViewModel){
    IconButton(
        onClick = { onAddGroup() },
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary)
            .padding(start = 6.dp, top = 6.dp)
            .size(65.dp)
            .clip(shape = RoundedCornerShape(15.dp)),
        content = {
            Icon(Icons.Filled.AddCircle ,
                contentDescription = stringResource(R.string.add_group) ,
                tint = MaterialTheme.colorScheme.onTertiary)
        }
    )
    if (vm.showAddMembrerDialog) { AddFriendDialog(vm) }
}


@Composable
fun AddFriendDialog(vm : GroupeScreenViewModel,
) {
    var pseudoTyped by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {vm.CloseAddFriendDialog()},
        title = { Text("Ajouter un ami") },
        text = {
            OutlinedTextField(
                value = pseudoTyped,
                onValueChange = { pseudoTyped = it },
                label = { Text("Pseudo") },
                isError = vm.memberNotFound,
                supportingText = {
                    if (vm.memberNotFound) {
                        Text(stringResource(R.string.friend_not_found))
                    }
                },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { vm.onConfirm(pseudoTyped.trim()) },
                enabled = pseudoTyped.isNotBlank()
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = {vm.CloseAddFriendDialog()}) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}







