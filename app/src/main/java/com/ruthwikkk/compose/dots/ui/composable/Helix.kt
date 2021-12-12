package com.ruthwikkk.compose.dots.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Helix() {

    val radius = 20f
    val yScale = 120f
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000,
                delayMillis = 0,
                easing = LinearEasing
            ),
            RepeatMode.Restart,
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {

        for (i in 0..360 step 30) {
            drawCircle(
                color = Color(0xFF393939),
                radius = radius - (cos(Math.toRadians((i + translateAnim).toDouble())) * radius / 2).toFloat(),
                center = Offset(
                    (190 + i.toFloat() * 2f),
                    this.center.y + sin(Math.toRadians((i + translateAnim).toDouble())).toFloat() * yScale
                )
            )

            drawCircle(
                color = Color(0xFF393939),
                radius = radius - (cos(Math.toRadians((i + 180 - translateAnim).toDouble())) * radius / 2).toFloat(),
                center = Offset(
                    (190 + i.toFloat() * 2f),
                    this.center.y + sin(-Math.toRadians((i + translateAnim).toDouble())).toFloat() * yScale
                )
            )
        }
    }
}