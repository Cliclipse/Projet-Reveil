package com.example.projetreveil.ui.theme.utilities

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.projetreveil.R
import kotlin.math.cos
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun Logo(
    modifier: Modifier = Modifier,
    size: Float = 75f
){
    val imageModifier = modifier.size(size.dp).clip(CircleShape)
    Image(
        painter = painterResource(R.drawable.penguin),
        contentDescription = "LogoPenguin",
        contentScale = ContentScale.Crop,
        modifier = imageModifier
    )
}

@Preview(showBackground = true)
@Composable
fun HeadedTextField(text : String = "Placeholder",
                    textField : String = "textField",
                    onTextChange : (String) -> Unit = {}
) {
    Text(text = text, color = MaterialTheme.colorScheme.onBackground)
    TextField(
        value = textField,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor =
                MaterialTheme.colorScheme.surface,
            unfocusedContainerColor =
                MaterialTheme.colorScheme.surface,
            focusedTextColor =
                MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor =
                MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor =
                MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor =
                MaterialTheme.colorScheme.outline,
            cursorColor =
                MaterialTheme.colorScheme.tertiary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DigitalClock(
    hour: Int = 7,
    minute: Int = 30
){
    Card(modifier = Modifier.width(200.dp).height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    )
    {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = String.format("%02d:%02d", hour, minute),
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopFlag(modifier: Modifier = Modifier,
    text: String = "Placeholder"
){
    Box(modifier = modifier.width(350.dp)
        .height(60.dp)
        .rotate(-45f)
        .offset(x = (-100).dp, y = (-40).dp)
        .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center){
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadialMenuPreviewExpended(
    isExendedInitialValue: Boolean = true
){
    RadialMenu(
        isExendedInitialValue = isExendedInitialValue
    )
}
@Preview(showBackground = true)
@Composable
fun RadialMenu(
    modifier: Modifier = Modifier,
    isInMenu: Boolean = true,
    isOnline: Boolean = true,
    onGoToMenuOrGroupClicked: () -> Unit = {},
    onGoToConfigureAlarmClicked: () -> Unit = {},
    onGoToSettingsClicked: () -> Unit = {},
    isExendedInitialValue: Boolean = false
){
    var isExpended by remember { mutableStateOf(isExendedInitialValue) }

    val animationDistProgressBtn1 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn1InAnimation"
    )

    val animationDistProgressBtn2 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn2InAnimation"
    )

    val animationDistProgressBt3 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "radialMenuDistBtn3InAnimation"
    )

    val animationSizeProgressBackground1 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "radialMenuSizeBackground1InAnimation"
    )

    val animationSizeProgressBackground2 by animateFloatAsState(
        targetValue = if (isExpended) 1f else 0f,
        animationSpec = tween(delayMillis = 200, durationMillis = 200, easing = FastOutSlowInEasing),
        label = "radialMenuSizeBackground2InAnimation"
    )

    val sizeRadialBtns = 60f;
    val sizeMainRadialBtn = 70f;

    // Home
    Box(modifier = modifier.fillMaxSize()){
        if (!isInMenu){
            // Home
            RadialButton(
                Modifier.align(Alignment.BottomEnd),
                icon = Icons.Default.Home,
                size = sizeRadialBtns,
                angle = 0f,
                progressDist = animationDistProgressBtn1,
                onClick = { onGoToMenuOrGroupClicked() }
            )
        }
        else{
            if (isOnline){
                // Group
                RadialButton(
                    Modifier.align(Alignment.BottomEnd),
                    icon = Icons.Default.Person,
                    size = sizeRadialBtns,
                    angle = 0f,
                    progressDist = animationDistProgressBtn1,
                    onClick = {
                        if (isOnline){
                            onGoToMenuOrGroupClicked()
                        }
                    }
                )
            }
        }


        // New Alarm
        RadialButton(
            Modifier.align(Alignment.BottomEnd),
            icon = Icons.Default.AddCircle,
            size = sizeRadialBtns,
            angle = 45f,
            progressDist = animationDistProgressBtn2,
            onClick = { onGoToConfigureAlarmClicked() }
        )

        // Settings
        RadialButton(
            Modifier.align(Alignment.BottomEnd),
            icon = Icons.Default.Settings,
            size = sizeRadialBtns,
            angle = 90f,
            progressDist = animationDistProgressBt3,
            onClick = { onGoToSettingsClicked() }
        )

        Box(modifier = Modifier
            .size(sizeMainRadialBtn.dp)
            .align(Alignment.BottomEnd)
            .zIndex(3f)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable { isExpended = !isExpended },
            contentAlignment = Alignment.Center){
            Icon(
                imageVector = if (isExpended) Icons.Default.Menu else Icons.Default.MoreVert,
                contentDescription = "Delete Alarm",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        RadialBackground(modifier = Modifier.align(Alignment.BottomEnd),
            backgroundColor = MaterialTheme.colorScheme.onTertiary,
            progressSize = animationSizeProgressBackground1,
            offset = sizeMainRadialBtn / 2f
        )

        RadialBackground(modifier = Modifier.align(Alignment.BottomEnd),
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            progressSize = animationSizeProgressBackground2,
            offset = sizeMainRadialBtn / 2f
        )
    }
}

@Composable
fun RadialButton(modifier: Modifier = Modifier,
                 icon: ImageVector = Icons.Default.Home,
                 size: Float = 60f,
                 angle: Float = 0f,
                 progressDist: Float = 1f,
                 onClick: () -> Unit = {}
){
    val radius = 100f

    val radians = Math.toRadians(angle.toDouble())

    val x = -cos(radians).toFloat() * progressDist * radius
    val y = -sin(radians).toFloat() * progressDist * radius

    Box(modifier = modifier.size(size.dp)
        .zIndex(2f)
        .alpha(alpha = if (progressDist < 0.05f) 0f else 1f)
        .offset(x.dp, y.dp)
        .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
        .clip(shape = CircleShape)
        .clickable(enabled = progressDist > 0.9f) { onClick() },
        contentAlignment = Alignment.Center,
        ){
            Icon(
                imageVector = icon,
                contentDescription = "Delete Alarm",
                tint = MaterialTheme.colorScheme.onSecondary
            )
    }
}

@Composable
fun RadialBackground(modifier: Modifier,
                     backgroundColor: Color,
                     progressSize: Float,
                     offset: Float
){

    val radius = 320f * progressSize
    val offset = radius / 2f - offset

    Box(modifier = modifier
        .zIndex(1f)
        .offset(x = offset.dp, y = offset.dp)
        .size(radius.dp)
        .alpha(alpha = if (progressSize < 0.05f) 0f else 1f)
        .background(color = backgroundColor, shape = CircleShape)
        .clip(shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(120.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}