package com.example.projetreveil.ui.theme.utilities

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.projetreveil.R
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Logo(size : Float){
    val imageModifier = Modifier.size(size.dp).clip(CircleShape)
    Image(painter = painterResource(R.drawable.penguin) ,"LogoPenguin" , contentScale = ContentScale.Crop,  modifier = imageModifier)

}

@Composable
fun HeadedTextField(text : String , textField : String ,  onTextChange : (String) -> Unit) {
    Text(text = text)
    TextField(value = textField, onValueChange = onTextChange)
}

@Composable
fun DigitalClock(hour: Int, minute: Int){

    Card(modifier = Modifier.width(200.dp).height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
        )
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = String.format("%02d:%02d", hour, minute),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TopFlag(text: String){
    Box(modifier = Modifier.width(350.dp)
        .height(60.dp)
        .rotate(-45f)
        .offset(x = (-100).dp, y = (-40).dp)
        .background(Color.Black),
        contentAlignment = Alignment.Center){
        Text(
            text = text,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun RadialMenu(
    modifier: Modifier,
    onGoToGroupClicked: () -> Unit,
    onGoToMenuClicked: () -> Unit,
    onGoToConfigureAlarmClicked: () -> Unit){

    var isExpended by remember { mutableStateOf(false) }

    val animationDistProgressBtn1 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn1InAnimation"
    )

    val animationDistProgressBtn2 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn2InAnimation"
    )

    val animationDistProgressBt3 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn3InAnimation"
    )

    Box(modifier = modifier.fillMaxSize()){
        RadialButton(
            Modifier.align(Alignment.BottomEnd),
            text = "Menu",
            angle = 10f,
            progressDist = animationDistProgressBtn1,
            onClick = { onGoToMenuClicked() }
        )

        RadialButton(
            Modifier.align(Alignment.BottomEnd),
            text = "Group",
            angle = 45f,
            progressDist = animationDistProgressBtn2,
            onClick = { onGoToGroupClicked() }
        )

        RadialButton(
            Modifier.align(Alignment.BottomEnd),
            text = "New Alarm",
            angle = 80f,
            progressDist = animationDistProgressBt3,
            onClick = { onGoToConfigureAlarmClicked() }
        )

        Box(modifier = Modifier
            .size(70f.dp)
            .align(Alignment.BottomEnd)
            .zIndex(2f)
            .background(color = Color.Black, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable { isExpended = !isExpended },
            contentAlignment = Alignment.Center){
            Text(text = if (isExpended) "x" else "+", color = Color.White, fontSize = 30.sp)
        }
    }
}


@Composable
fun RadialButton(modifier: Modifier, text: String, angle: Float, progressDist: Float, onClick: () -> Unit){

    val radius = 130f;

    val radians = Math.toRadians(angle.toDouble())

    val x = -cos(radians).toFloat() * progressDist * radius
    val y = -sin(radians).toFloat() * progressDist * radius

    Box(modifier = modifier.size(70f.dp)
        .zIndex(1f)
        .alpha(alpha = if (progressDist < 0.05f) 0f else 1f)
        .offset(x.dp, y.dp)
        .background(color = Color.DarkGray, shape = CircleShape)
        .clip(shape = CircleShape)
        .clickable(enabled = progressDist > 0.9f) { onClick() },
        contentAlignment = Alignment.Center,
        ){
            Text(text = text, color = Color.White)
    }

}