package com.example.projetreveil.ui.theme.screens

import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun WelcomePage(name: String, modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxSize()) {
        // Bandeau social top


        // Horloge
        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center)
        {
            ClockTimeLive()
        }

        // Afficher heure


        // Liste Reveil


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
            style = Stroke(width = 4.dp.toPx())
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
                y = center.x + sin(angle).toFloat() * radius
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
                y = center.x + sin(hourAngle).toFloat() * hourHandLength
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
                y = center.x + sin(minuteAngle).toFloat() * minuteHandLength
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

    ClockView(hour = time.get(Calendar.HOUR), minute = time.get(Calendar.MINUTE))
}