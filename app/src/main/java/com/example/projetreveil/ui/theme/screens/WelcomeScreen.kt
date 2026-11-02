package com.example.projetreveil.ui.theme.screens

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.DataStoreSettingsApp
import com.example.projetreveil.ui.theme.RoomDatas.Alarm
import com.example.projetreveil.ui.theme.utilities.DigitalClock
import com.example.projetreveil.ui.theme.utilities.RadialMenu
import com.example.projetreveil.ui.theme.utilities.TopFlag
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun WelcomePage(name: String,
                context: Context,
                modifier: Modifier = Modifier,
                alarmsViewModel: AlarmsViewModel,
                onGoToConfigureAlarmClicked: () -> Unit,
                onGoToGroupClicked: () -> Unit,
                onGoToSettingsClicked: () -> Unit,
                settingsDataStore: DataStoreSettingsApp) {

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            // Bandeau social top

            TopFlag("PERSO")

            Spacer(modifier = Modifier.height(40.dp))

            // Horloge
            Box(modifier = Modifier.fillMaxWidth().height(380.dp), contentAlignment = Alignment.Center){
                ClockTimeLive()
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Liste Reveil
            AlarmList(context = context, alarmsViewModel, modifier = Modifier.height(340.dp))

        }

        // Bouton + menu radial bas droite
        RadialMenu(
            modifier = Modifier.fillMaxSize().padding(34f.dp),
            isInMenu = true,
            onGoToMenuOrGroupClicked = { onGoToGroupClicked() },
            onGoToConfigureAlarmClicked = { onGoToConfigureAlarmClicked() },
            onGoToSettingsClicked = { onGoToSettingsClicked() }
        )
    }
}

@Composable
fun ClockView(modifier: Modifier = Modifier, hour: Int = 10, minute: Int = 10){

    val clockColor : Color = MaterialTheme.colorScheme.primary
    val needlesColor : Color = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = modifier.size(280.dp)){
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2

        drawCircle(
            color = clockColor,
            radius = radius,
            style = Stroke(width = 2.dp.toPx())
        )

        for (i in 0 until 60){

            // Clock circle and marks
            val angle = Math.toRadians(i * 6.0)

            val isHour = i % 5 == 0

            val isBigHour = i % 15 == 0

            val tickLength = if (isBigHour) 38.dp.toPx() else if (isHour) 22.dp.toPx() else 12.dp.toPx()
            val tickWidth = if (isBigHour) 2.dp.toPx() else if (isHour) 2.dp.toPx() else 1.dp.toPx()

            val startRadius = radius - tickLength

            val start = Offset(
                x = center.x + cos(angle).toFloat() * startRadius,
                y = center.y + sin(angle).toFloat() * startRadius
            )

            val end = Offset(
                x = center.x + cos(angle).toFloat() * radius,
                y = center.y + sin(angle).toFloat() * radius
            )

            drawLine(
                color = clockColor,
                start,
                end,
                strokeWidth = tickWidth
            )
        }

        // Hour indicator
        val hourAngle = Math.toRadians(((hour % 12) * 30 + (minute % 60) * 0.5) - 90)

        val hourHandLength = radius * 0.6f

        val endHour = Offset(
            x = center.x + cos(hourAngle).toFloat() * hourHandLength,
            y = center.y + sin(hourAngle).toFloat() * hourHandLength
        )

        drawLine(
            color = needlesColor,
            start = center,
            end = endHour,
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )


        // Min indicator
        val minuteAngle = Math.toRadians((minute % 60) * 6.0 - 90)

        val minuteHandLength = radius * 0.8f

        val endMinute = Offset(
            x = center.x + cos(minuteAngle).toFloat() * minuteHandLength,
            y = center.y + sin(minuteAngle).toFloat() * minuteHandLength
        )

        drawLine(
            color = needlesColor,
            start = center,
            end = endMinute,
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun ClockTimeLive(){
    var time by remember {
        mutableStateOf(Calendar.getInstance())
    }

    LaunchedEffect(Unit) {
        while (true){
            time = Calendar.getInstance()
            delay(1000.milliseconds)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        ClockView(hour = time.get(Calendar.HOUR), minute = time.get(Calendar.MINUTE))

        Spacer(modifier = Modifier.height(24.dp))

        DigitalClock(
            hour = time.get(java.util.Calendar.HOUR),
            minute = time.get(java.util.Calendar.MINUTE)
        )
    }
}

@Composable
fun AlarmItem(context: Context,
              alarm: Alarm,
              onEnabledChange: (Boolean) -> Unit,
              onDeleteClicked: () -> Unit
){

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Card(modifier = Modifier.width(300.dp).height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ){
            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Switch(
                    checked = alarm.enabled,
                    onCheckedChange = {
                        onEnabledChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                        checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                        checkedBorderColor = MaterialTheme.colorScheme.tertiary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.outline,
                        uncheckedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = String.format("%02d:%02d", alarm.hour, alarm.minutes),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                AlarmItemOptionsPreview(
                    modifier = Modifier.width(28.dp),
                    alarm = alarm
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.width(48.dp).fillMaxHeight(),
                    onClick = { onDeleteClicked() },
                    shape = RoundedCornerShape(5.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.7f),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Alarm"
                    )
                }
            }
        }
    }
}

@Composable
fun GhostAlarmItem(){

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Card(modifier = Modifier.width(300.dp).height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ){
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Text(modifier = Modifier.weight(2f),
                    text = "No Alarm",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.weight(2f),
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Delete Alarm"
                )
            }
        }
    }
}

@Composable
fun AlarmItemOptionsPreview(modifier: Modifier = Modifier, alarm: Alarm){

    Column(modifier = modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(modifier = Modifier.weight(6f),
            imageVector = Icons.Default.Notifications,
            contentDescription = if (alarm.vibration) "Alarm vibration enabled" else "Alarm vibration disabled",
            tint = if (alarm.vibration) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onTertiary
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(modifier = Modifier.weight(6f),
            imageVector = Icons.Default.DateRange,
            contentDescription = if (alarm.daily) "Alarm daily enabled" else "Alarm daily disabled",
            tint = if (alarm.daily) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Composable
fun AlarmList(context: Context, alarmsViewModel: AlarmsViewModel, modifier: Modifier = Modifier){

    val alarms by alarmsViewModel.alarms.collectAsStateWithLifecycle()

    Column() {
        Text(
            text = stringResource(R.string.personal_alarm),
            modifier = Modifier.fillMaxWidth().height(30.dp).padding(horizontal = 50.dp, vertical = 0.dp),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().height(300.dp).clipToBounds()) {
            items(items = alarms, key = {alarm -> alarm.id }) { alarm ->
                AlarmItem(context = context,
                    alarm = alarm,
                    onEnabledChange = {
                            newEnabledValue ->
                        updateAlarmEnableState(
                            context = context,
                            alarmsViewModel = alarmsViewModel,
                            alarm = alarm,
                            enabled = newEnabledValue
                        )
                    },
                    onDeleteClicked = {
                        AlarmsUtilities().StopAlarm(context, alarm.id)
                        alarmsViewModel.DeleteAlarm(alarm);
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            if (alarms.isEmpty()){
                item {
                    GhostAlarmItem()
                }
            }
        }

    }
}

fun updateAlarmEnableState(context: Context, alarmsViewModel: AlarmsViewModel, alarm: Alarm, enabled: Boolean){

    alarmsViewModel.UpdateAlarmEnabled(alarm = alarm, enabled = enabled)

    AlarmsUtilities().SetAlarm(
        context = context,
        alarmID = alarm.id,
        hour = alarm.hour,
        minutes = alarm.minutes,
        enabled = enabled,
        vibration = alarm.vibration,
        daily = alarm.daily
    )
}

