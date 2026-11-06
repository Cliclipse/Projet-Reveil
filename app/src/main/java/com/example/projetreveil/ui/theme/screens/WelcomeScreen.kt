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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.AlarmsManagement.AlarmsUtilities
import com.example.projetreveil.ui.theme.RoomDatas.Alarm
import com.example.projetreveil.ui.theme.data.Objet.Users
import com.example.projetreveil.ui.theme.utilities.ConfirmActionPopup
import com.example.projetreveil.ui.theme.utilities.DigitalClock
import com.example.projetreveil.ui.theme.utilities.RadialMenu
import com.example.projetreveil.ui.theme.utilities.TopFlag
import com.example.projetreveil.ui.theme.viewModel.AlarmsViewModel
import com.example.projetreveil.ui.theme.viewModel.WelcomeScreenViewModel
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
                onModifySelectedAlarmClicked: (Alarm) -> Unit
) {

    val alarms by alarmsViewModel.alarms.collectAsStateWithLifecycle()

    val welcomeScreenViewModel: WelcomeScreenViewModel = viewModel()

    LaunchedEffect(Unit){
        welcomeScreenViewModel.loadCurrentUser()
    }

    val currentUser = welcomeScreenViewModel.currentUser

    WelcomeContent(modifier = Modifier,
        alarms = alarms,
        currentUser = currentUser,
        onAlarmEnabledChange = {
            newEnabledValue, alarm ->

            updateAlarmEnableState(
                context = context,
                alarmsViewModel = alarmsViewModel,
                alarm = alarm,
                enabled = newEnabledValue
            )
        },
        onAlarmDelete = {
            deleteAlarm(
                context = context,
                alarmsViewModel = alarmsViewModel,
                alarm = it
            )
        },
        onGoToConfigureAlarmClicked = { onGoToConfigureAlarmClicked() },
        onGoToGroupClicked = { onGoToGroupClicked() },
        onGoToSettingsClicked = { onGoToSettingsClicked() },
        onModifySelectedAlarmClicked = { onModifySelectedAlarmClicked(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier,
    alarms: List<Alarm> = listOf(
        Alarm(1, 7, 30, true, true, true),
        Alarm(2, 21, 25, false, false, true)
    ),
    currentUser: Users? = null,
    onAlarmEnabledChange: (Boolean, Alarm) -> Unit = {_, _ -> },
    onAlarmDelete: (Alarm) -> Unit = {_ -> },
    onGoToConfigureAlarmClicked: () -> Unit = {},
    onGoToGroupClicked: () -> Unit = {},
    onGoToSettingsClicked: () -> Unit = {},
    onModifySelectedAlarmClicked: (Alarm) -> Unit = {}
){
    Box(modifier = modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            // Bandeau social top
            TopFlag(text = stringResource(R.string.perso))

            val userText = if (currentUser == null)
                stringResource(R.string.offline)
            else (stringResource(R.string.welcome) + " " + currentUser.name)

            Text(
                modifier = Modifier.align(Alignment.End)
                    .fillMaxWidth(0.7f)
                    .padding(end = 24.dp),
                fontSize = 24.sp,
                text = userText,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Horloge
            Box(modifier = Modifier.fillMaxWidth().height(380.dp), contentAlignment = Alignment.Center){
                ClockTimeLive()
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Liste Reveil
            AlarmList(
                alarms = alarms,
                onAlarmEnabledChange = { newEnabled, alarm -> onAlarmEnabledChange(newEnabled, alarm)},
                onAlarmDelete = { onAlarmDelete(it) },
                onModifySelectedAlarmClicked = { onModifySelectedAlarmClicked(it) }
            )
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

@Preview(showBackground = true)
@Composable
fun ClockView(modifier: Modifier = Modifier,
              hour: Int = 10,
              minute: Int = 10
){

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

    ClockTimeContent(
        hour = time.get(java.util.Calendar.HOUR_OF_DAY),
        minutes = time.get(java.util.Calendar.MINUTE)
    )
}

@Preview(showBackground = true)
@Composable
fun ClockTimeContent(
    modifier: Modifier = Modifier,
    hour: Int = 7,
    minutes: Int = 30
){
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ClockView(hour = hour % 12,
            minute = minutes
        )

        Spacer(modifier = Modifier.height(24.dp))

        DigitalClock(
            hour = hour,
            minute = minutes
        )
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 60)
@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarm: Alarm = Alarm(
        id = -1,
        hour = 7,
        minutes = 30,
        enabled = true,
        vibration = true,
        daily = false
    ),
    onEnabledChange: (Boolean) -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onModifyClicked: (Alarm) -> Unit = {}
){
    Card(modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = {
            onModifyClicked(alarm)
        }
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

            AlarmItemOptions(
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

@Preview(showBackground = true, widthDp = 300, heightDp = 60)
@Composable
fun GhostAlarmItem(
    modifier: Modifier = Modifier
){
    Card(modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.weight(0.2f))

            Text(modifier = Modifier.weight(2f),
                text = stringResource(R.string.no_alarm),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(0.6f))

            Icon(
                modifier = Modifier.weight(0.6f),
                imageVector = Icons.Default.Notifications,
                contentDescription = "Delete Alarm"
            )

            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

@Preview(showBackground = true, widthDp = 50, heightDp = 100)
@Composable
fun AlarmItemOptions(
    modifier: Modifier = Modifier,
    alarm: Alarm = Alarm(
        id = -1,
        hour = 7,
        minutes = 30,
        enabled = true,
        vibration = true,
        daily = true
    )
){
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
fun AlarmList(
    modifier: Modifier = Modifier,
    alarms: List<Alarm> = listOf(
        Alarm(1, 7, 30, true, true, true),
        Alarm(2, 21, 25, false, false, true)
    ),
    onAlarmEnabledChange: (Boolean, Alarm) -> Unit = {_, _ -> },
    onAlarmDelete: (Alarm) -> Unit = {},
    onModifySelectedAlarmClicked: (Alarm) -> Unit = {}
)
{
    var alarmToDelete: Alarm? by rememberSaveable {
        mutableStateOf<Alarm?>(null)
    }

    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.personal_alarm),
            modifier = Modifier.fillMaxWidth().height(30.dp).padding(horizontal = 50.dp, vertical = 0.dp),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth(0.8f)
            .fillMaxHeight()
            .clipToBounds(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(items = alarms, key = {alarm -> alarm.id }) { alarm ->
                AlarmItem(
                    modifier = Modifier.height(70.dp),
                    alarm = alarm,
                    onEnabledChange = {
                            newEnabledValue ->
                        onAlarmEnabledChange(newEnabledValue, alarm)
                    },
                    onDeleteClicked = {

                        alarmToDelete = alarm
                    },
                    onModifyClicked = { onModifySelectedAlarmClicked(it) }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            if (alarms.isEmpty()){
                item {
                    GhostAlarmItem(
                        modifier = Modifier.height(70.dp)
                    )
                }
            }
        }

        ConfirmActionPopup(display = alarmToDelete != null,
            onDismiss = {
                alarmToDelete = null
            },
            onConfirm = {
                if (alarmToDelete != null){
                    onAlarmDelete(alarmToDelete!!)

                    alarmToDelete = null
                }
            },
            title = stringResource(R.string.delete_alarm_title),
            text = stringResource(R.string.delete_alarm_text),
            confirmText = stringResource(R.string.confirm_delete)
        )

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

fun deleteAlarm(context: Context, alarmsViewModel: AlarmsViewModel, alarm: Alarm){

    AlarmsUtilities().StopAlarm(context, alarm.id)
    alarmsViewModel.DeleteAlarm(alarm)
}

