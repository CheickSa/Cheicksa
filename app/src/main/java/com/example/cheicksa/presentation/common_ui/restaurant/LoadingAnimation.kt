package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheicksa.ui.theme.CheicksaTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {

    val items = listOf(
        remember{ Animatable(initialValue = 0f) },
        remember{ Animatable(initialValue = 0f) },
        remember{ Animatable(initialValue = 0f) }
    )

    items.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable ){
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )

        }
    }
    val distance = with(LocalDensity.current) { 10.dp.toPx() }
    val itemValues = items.map { it.value }
    Box (
        modifier = modifier
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemValues.forEach { item ->
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .graphicsLayer {
                            this.translationY = -item * distance
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )


                )
            }
        }
    }

}

@Preview
@Composable
fun _LoadingAnimation() {
    CheicksaTheme {
        LoadingAnimation()
    }
}