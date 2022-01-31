package com.ruthwikkk.compose.dots.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun MovingDots() {

    val dots = listOf(
        remember { Animatable(360f) },
        remember { Animatable(360f) },
        remember { Animatable(360f) },
        remember { Animatable(360f) }
    )

    val xAnimation = remember { Animatable(0f) }

    val tTime = 500
    val size = dots.size
    val delay = 0

    val dotRadius = 40f
    val axisRadius = 2 * dotRadius

    val duration = 2 * tTime + size * 2 * delay + (size - 1) * 2 * tTime


    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * (tTime + delay).toLong())
            animatable.animateTo(
                targetValue = 0f, animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = duration
                        360f at 0 with LinearEasing
                        180f at tTime with LinearEasing
                        180f at (tTime + delay) + (size - index - 1) * 2 * (tTime + delay) with LinearEasing
                        0f at (2 * tTime + delay) + (size - index - 1) * 2 * (tTime + delay)
                        0f at duration
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        xAnimation.animateTo(
            targetValue = 480f, animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = (size * (tTime + delay))
                    0f at 0 with LinearEasing
                    120f at tTime with LinearEasing
                    240f at 2 * tTime with LinearEasing
                    360f at 3 * tTime with LinearEasing
                    480f at 4 * tTime with LinearEasing
                },
                repeatMode = RepeatMode.Reverse,
            )
        )
    }

    val dotsMap = dots.map { it.value }.reversed()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFF064ADA))
    ) {
        dotsMap.forEachIndexed { index, state ->
            val offsetDot = Offset(center.x - index * 3 * dotRadius + 180, center.y)
            drawCircle(Color(0XFFF0F0F0), dotRadius, findCentre(state, offsetDot, axisRadius.toInt()))
        }
        val offsetDotXMoving = Offset(center.x - size * 3 * dotRadius + 180 + xAnimation.value, center.y)
        drawCircle(Color(0XFFF0F0F0), dotRadius, findCentre(0f, offsetDotXMoving, axisRadius.toInt()))

    }
}

fun findCentre(angle: Float, origin: Offset, radius: Int): Offset {
    val x = radius * cos(Math.toRadians(angle.toDouble())) + origin.x
    val y = radius * sin(Math.toRadians(angle.toDouble())) + origin.y
    return Offset(x.toFloat(), y.toFloat())
}