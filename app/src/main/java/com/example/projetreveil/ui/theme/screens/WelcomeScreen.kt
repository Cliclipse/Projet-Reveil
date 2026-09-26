package com.example.projetreveil.ui.theme.screens

import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetreveil.ui.theme.utilities.DigitalClock
import com.example.projetreveil.ui.theme.utilities.TopFlag
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds


data class AlarmData(  // WIP ONLY FOR TESTS in ALARM LIST
    val hour: Int,
    val minute: Int,
    val enabled: Boolean
)

@Composable
fun WelcomePage(name: String, modifier: Modifier = Modifier) {

    val exempleAlarms = remember {
        mutableStateListOf(
            AlarmData(7, 25, false),
            AlarmData(10, 40, true),
            AlarmData(5, 10, true),
            AlarmData(15, 59, false),
            AlarmData(12, 32, true),
            AlarmData(15, 59, false),
            AlarmData(15, 59, false),
            AlarmData(15, 59, false)
        )
    }

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
        AlarmList(exempleAlarms, modifier = Modifier.height(340.dp))

        // Bouton + bas droite
    }
}

@Composable
fun ClockView(modifier: Modifier = Modifier, hour: Int = 10, minute: Int = 10){
    Canvas(modifier = modifier.size(280.dp)){
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2

        drawCircle(
            color = Color.Black,
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
                color = Color.Black,
                start,
                end,
                strokeWidth = tickWidth
            )


            // Hour indicator
            val hourAngle = Math.toRadians(((hour % 12) * 30 + (minute % 60) * 0.5) - 90)

            val hourHandLength = radius * 0.6f

            val endHour = Offset(
                x = center.x + cos(hourAngle).toFloat() * hourHandLength,
                y = center.y + sin(hourAngle).toFloat() * hourHandLength
            )

            drawLine(
                color = Color.Blue,
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
                color = Color.Blue,
                start = center,
                end = endMinute,
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
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
fun AlarmItem(alarm: AlarmData, onEnabledChange: (Boolean) -> Unit){

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Card(modifier = Modifier.width(300.dp).height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ){
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Switch(
                    checked = alarm.enabled,
                    onCheckedChange = {
                        // Lambda pour pouvoir réagir au fait que cet alarm doit etre considérée comme activée
                        onEnabledChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Green,
                        uncheckedThumbColor = Color.LightGray,
                        uncheckedTrackColor = Color.DarkGray
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = String.format("%02d:%02d", alarm.hour, alarm.minute),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@Composable
fun AlarmList(alarms: MutableList<AlarmData>, modifier: Modifier = Modifier){

    Column() {
        Text(
            text = "Reveil Perso",
            modifier = Modifier.fillMaxWidth().height(30.dp).padding(horizontal = 50.dp, vertical = 0.dp),
            fontSize = 28.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(alarms) { alarm ->
                AlarmItem(alarm = alarm,
                    onEnabledChange = { newEnabledValue ->
                        val index = alarms.indexOf(alarm)

                        alarms[index] = alarm.copy(
                            enabled = newEnabledValue
                        )
                    })
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

