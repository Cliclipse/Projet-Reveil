package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.utilities.RadialMenu
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    name: String,
    modifier: Modifier = Modifier,
    onGoToConfigureAlarmClicked: () -> Unit,
    onGoToMenuClicked: () -> Unit,
    onGoToSettingsClicked: () -> Unit,
    settingsDataStore: DataStoreSettingsApp)
{
    val scope = rememberCoroutineScope()
    val nightMode by settingsDataStore.nightMode.collectAsStateWithLifecycle(initialValue = false)

    Box(modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)){

        Column(modifier = Modifier.fillMaxSize().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            // Titre Page
            Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.settings),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 44.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(100.dp))

            // Liste des paramètres
            Column(modifier = Modifier.width(300.dp)
                .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
            ) {

                // Night Mode
                SwitchSetting(
                    name = stringResource(R.string.night_mode),
                    checked = nightMode,
                    onCheckedChange = { scope.launch { settingsDataStore.defineNightMode(!nightMode) } }
                )
            }
        }

        // Radial Menu bottom right
        RadialMenu(
            modifier = Modifier.fillMaxSize().padding(34f.dp),
            isInMenu = false,
            onGoToMenuOrGroupClicked = { onGoToMenuClicked() },
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
    Row(modifier = modifier.fillMaxWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically) {

        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                checkedBorderColor = MaterialTheme.colorScheme.tertiary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(text = name,
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface)
    }
}