package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.utilities.RadialMenu
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    name: String,
    modifier: Modifier = Modifier,
    onGoToConfigureAlarmClicked: () -> Unit,
    onGoToMenuClicked: () -> Unit,
    onGoToGroupClicked: () -> Unit,
    onGoToSettingsClicked: () -> Unit,
    settingsDataStore: DataStoreSettingsApp)
{
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()){

        Column(modifier = Modifier.fillMaxSize().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            // Titre Page
            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Settings",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(100.dp))

            // Liste des paramètres
            Column(modifier = Modifier.width(300.dp)) {

                // Night Mode
                val nightMode by settingsDataStore.nightMode.collectAsStateWithLifecycle(initialValue = false)
                SwitchSetting(
                    name = "NightMode",
                    checked = nightMode,
                    onCheckedChange = { scope.launch { settingsDataStore.defineNightMode(!nightMode) } }
                )
            }
        }

        // Bouton + menu radial bas droite
        RadialMenu(
            modifier = Modifier.fillMaxSize().padding(34f.dp),
            onGoToMenuClicked = { onGoToMenuClicked() },
            onGoToGroupClicked = { onGoToGroupClicked() },
            onGoToConfigureAlarmClicked = { onGoToConfigureAlarmClicked() },
            onGoToSettingsClicked = { onGoToSettingsClicked() }
        )
    }
}

@Composable
fun SwitchSetting(name: String,
                  modifier : Modifier = Modifier,
                  checked : Boolean,
                  onCheckedChange : () -> Unit){
    Row(modifier = modifier) {

        Switch(
            modifier = modifier.weight(1f),
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(text = name,
            modifier = Modifier.weight(3f).align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onBackground)
    }
}