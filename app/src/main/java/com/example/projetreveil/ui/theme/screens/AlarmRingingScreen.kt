package com.example.projetreveil.ui.theme.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel

@Composable
fun AlarmRingingPage(
    name: String,
    modifier: Modifier = Modifier,
    context: Context,
    alarmsViewModel: AlarmsViewModel,
    alarmID: Int,
    alarmTime: String = "07:30",
    onSnoozeClicked: () -> Unit,
    onStopClicked: () -> Unit
    ){

    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 38.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Alarme en cours",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(54.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Reveil en cours",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground

        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Reveille toi ! Il est l'heure !",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary

        )

        Spacer(Modifier.height(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(28.dp))
        ){
            Text(
                text = alarmTime,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary

            )

            Text(
                text = "SONNERIE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)

            )
        }

        Spacer(Modifier.height(48.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){

            /*
            Disabled for now, see if we have time
            // Snooze Btn
            AlarmRingingActionButton(
                icon = Icons.Default.DateRange,
                primaryText = "Snooze",
                secondaryText = "5 minutes",
                primaryTextColor = MaterialTheme.colorScheme.onBackground,
                secondaryTextColor = MaterialTheme.colorScheme.secondary,
                btnColor = MaterialTheme.colorScheme.secondary,
                btnContentColor = MaterialTheme.colorScheme.onSecondary,
                onBtnClicked = { onSnoozeClicked() }
            )
            */

            // Confirm Btn
            AlarmRingingActionButton(
                icon = Icons.Default.Check,
                primaryText = "Arreter",
                secondaryText = "",
                primaryTextColor = MaterialTheme.colorScheme.onBackground,
                secondaryTextColor = MaterialTheme.colorScheme.tertiary,
                btnColor = MaterialTheme.colorScheme.tertiary,
                btnContentColor = MaterialTheme.colorScheme.onTertiary,
                onBtnClicked = {

                    AlarmsUtilities().StopAlarm(context = context, alarmID)

                    onStopClicked()
                }
            )
        }

    }
}

@Composable
fun AlarmRingingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    primaryText: String,
    secondaryText: String,
    btnColor: Color,
    btnContentColor: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    onBtnClicked: () -> Unit){

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onBtnClicked,
            modifier = Modifier.size(76.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = btnColor,
                contentColor = btnContentColor
            )
        ) {
            Icon(
                    imageVector = icon,
            contentDescription = primaryText
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = primaryText,
            fontWeight = FontWeight.Bold,
            color = primaryTextColor
        )

        Text(
            text = secondaryText,
            color = secondaryTextColor
        )
    }
}