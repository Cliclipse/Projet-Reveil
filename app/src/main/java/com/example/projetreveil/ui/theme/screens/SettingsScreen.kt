package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.datastores.DataStoreSettingsApp
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    name: String,
    modifier: Modifier = Modifier,
    onGoBackClicked: () -> Unit,
    settingsDataStore: DataStoreSettingsApp
)
{
    val nightMode by settingsDataStore.nightMode.collectAsStateWithLifecycle(initialValue = false)

    val scope = rememberCoroutineScope()

    SettingsContent(
        modifier = modifier,
        nightMode = nightMode,
        onNightModeCheckedChanged = {
            scope.launch {
                settingsDataStore.defineNightMode(!nightMode)
            }
        },
        onGoBackClicked = {
            onGoBackClicked()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    nightMode: Boolean = false,
    onNightModeCheckedChanged: (Boolean) -> Unit = {},
    onGoBackClicked: () -> Unit = {}
){
    Box(modifier = modifier.fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ){

        Column(modifier = Modifier.fillMaxSize()
            .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.weight(0.1f))

            // Titre Page
            Box(contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.settings),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 44.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.weight(0.1f))

            // Liste des paramètres
            Column(modifier = Modifier.width(300.dp)
                .background(color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Night Mode
                SwitchSetting(
                    name = stringResource(R.string.night_mode),
                    checked = nightMode,
                    onCheckedChange = {
                        onNightModeCheckedChanged(it)
                    }
                )
            }

            Spacer(modifier = Modifier.weight(0.4f))

            ConfirmButton(
                icon = Icons.Default.Done,
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = {
                    onGoBackClicked()
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Done,
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary,
    onClick: () -> Unit = {}
){
    IconButton(modifier = modifier.size(76.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = {
            onClick()
        },
        content = {
            Icon(
                modifier = Modifier.fillMaxSize(0.5f),
                imageVector = icon,
                contentDescription = "Confirm Settings"
            )
        }
    )
}

@Preview(showBackground = true, widthDp = 300)
@Composable
fun SwitchSetting(
    modifier : Modifier = Modifier,
    name: String = "Placeholder",
    checked : Boolean = true,
    onCheckedChange : (Boolean) -> Unit = {}){
    Row(modifier = modifier.fillMaxWidth()
        .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                checkedBorderColor = MaterialTheme.colorScheme.tertiary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.weight(0.6f))

        Text(text = name,
            modifier = Modifier.weight(1f)
                .padding(end = 6.dp)
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Right
        )
    }
}