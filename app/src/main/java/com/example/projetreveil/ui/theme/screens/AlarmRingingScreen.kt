package com.example.projetreveil.ui.theme.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel

@Composable
fun AlarmRingingPage(
    name: String,
    modifier: Modifier = Modifier,
    context: Context,
    alarmsViewModel: AlarmsViewModel,
    alarmID: Int,
    onSnoozeClicked: () -> Unit,
    onStopClicked: () -> Unit
    ){

    val associatedAlarms by alarmsViewModel.GetAlarmsWithID(alarmID = alarmID).collectAsStateWithLifecycle()

    val alarmText = associatedAlarms.firstOrNull()?.let {
        "%02d:%02d".format(it.hour, it.minutes)
    } ?: "07:30"

    AlarmRingingContent(
        alarmText = alarmText,
        onStopClicked = {
            AlarmsUtilities().StopAlarm(context = context, alarmID)

            onStopClicked()
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun AlarmRingingContent(
    modifier: Modifier = Modifier,
    alarmText: String = "07:30",
    onStopClicked: () -> Unit = {}
    ){
    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 38.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Spacer(Modifier.weight(0.3f))

        AlarmRingingTitleHeader(
            icon = Icons.Default.Notifications,
            title = stringResource(R.string.alarm_in_progress),
            text = stringResource(R.string.alarm_message),
            titleColor = MaterialTheme.colorScheme.onBackground,
            textColor = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.weight(0.3f))

        AlarmTimeDisplay(
            alarmText = alarmText,
            backgroundColor = MaterialTheme.colorScheme.primary,
            textsColor = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.weight(0.6f))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){

            /*
            Disabled for now, see if we have time
            // Snooze Btn
            AlarmRingingActionButton(
                icon = Icons.Default.DateRange,
                primaryText = stringResource(R.string.snooze),
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
                primaryText = stringResource(R.string.stop),
                secondaryText = "",
                primaryTextColor = MaterialTheme.colorScheme.onBackground,
                secondaryTextColor = MaterialTheme.colorScheme.tertiary,
                btnColor = MaterialTheme.colorScheme.tertiary,
                btnContentColor = MaterialTheme.colorScheme.onTertiary,
                onBtnClicked = {
                    onStopClicked()
                }
            )
        }

        Spacer(Modifier.weight(0.3f))
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmRingingTitleHeader(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Notifications,
    title: String = stringResource(R.string.alarm_in_progress),
    text: String = stringResource(R.string.alarm_message),
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    textColor: Color = MaterialTheme.colorScheme.secondary
){
    Column(modifier = modifier.fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Icon(
            imageVector = icon,
            contentDescription = "Alarme en cours",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(54.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = title,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = text,
            fontSize = 18.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmTimeDisplay(
    alarmText: String = "07:30",
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textsColor: Color = MaterialTheme.colorScheme.onPrimary
    ){
    Box(modifier = Modifier.fillMaxWidth()
        .background(backgroundColor,
            shape = RoundedCornerShape(28.dp)
        )
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.fillMaxWidth()
                .height(10.dp))

            Text(
                text = alarmText,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = textsColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.fillMaxWidth()
                .height(4.dp))

            Text(
                text = stringResource(R.string.ringtone),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textsColor.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.fillMaxWidth()
                .height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmRingingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Check,
    primaryText: String = stringResource(R.string.stop),
    secondaryText: String = "",
    btnColor: Color = MaterialTheme.colorScheme.tertiary,
    btnContentColor: Color = MaterialTheme.colorScheme.onTertiary,
    primaryTextColor: Color = MaterialTheme.colorScheme.onBackground,
    secondaryTextColor: Color = MaterialTheme.colorScheme.secondary,
    onBtnClicked: () -> Unit = {}){

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
            color = primaryTextColor,
            fontSize = 24.sp
        )

        Text(
            text = secondaryText,
            color = secondaryTextColor,
            fontSize = 16.sp
        )
    }
}