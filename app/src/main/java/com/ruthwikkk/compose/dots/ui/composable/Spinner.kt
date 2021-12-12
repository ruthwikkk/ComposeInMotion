package com.ruthwikkk.compose.dots.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Spinner() {

    val transition = rememberInfiniteTransition()
    val rotateAngleAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000,
                delayMillis = 0,
                easing = LinearEasing
            ),
            RepeatMode.Restart
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF212121))
    ) {

        if (rotateAngleAnim > 360) {
            val list = generatePointsForSpinnerEnd(220, this.center, rotateAngleAnim - 360)
            list.forEachIndexed { index, offset ->
                drawCircle(
                    color = Color.Cyan,
                    center = offset,
                    radius = if (index == 0) 20 - rotateAngleAnim % 20 else 20f,
                    style = Stroke(10f)
                )
            }
        } else {
            val list = generatePointsForSpinnerStart(220, this.center, rotateAngleAnim)
            list.forEachIndexed { index, offset ->
                drawCircle(
                    color = Color.Cyan,
                    center = offset,
                    radius = if (list.size - 1 == index) rotateAngleAnim % 20 else 20f,
                    style = Stroke(10f)
                )
            }
        }

    }
}

fun generatePointsForSpinnerStart(radius: Int, origin: Offset, angle: Float): ArrayList<Offset> {
    val list = ArrayList<Offset>()

    for (i in 0..angle.toInt() step 20) {
        val x = radius * cos(Math.toRadians(i.toDouble())) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble())) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}

fun generatePointsForSpinnerEnd(radius: Int, origin: Offset, angle: Float): ArrayList<Offset> {
    val list = ArrayList<Offset>()

    val lowerLimit = ceil(angle/20.0) *20
    for (i in lowerLimit.toInt()..360 step 20) {
        val x = radius * cos(Math.toRadians(i.toDouble())) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble())) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}

@Composable
fun Spinner2() {

    val transition = rememberInfiniteTransition()
    val rotateAngleAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000,
                delayMillis = 0,
                easing = LinearEasing
            ),
            RepeatMode.Restart
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF212121))
    ) {

        if (rotateAngleAnim > 360) {
            val list = generatePointsForSpinnerEnd2(220, this.center, rotateAngleAnim - 360)
            list.forEach { offset ->
                drawCircle(
                    color = Color.Red,
                    center = offset,
                    radius = 20f,
                    style = Stroke(10f)
                )
            }
        } else {
            val list = generatePointsForSpinnerStart2(220, this.center, rotateAngleAnim)
            list.forEach { offset ->
                drawCircle(
                    color = Color.Red,
                    center = offset,
                    radius = 20f,
                    style = Stroke(10f)
                )
            }
        }

    }
}

fun generatePointsForSpinnerStart2(radius: Int, origin: Offset, angle: Float): ArrayList<Offset> {
    val list = ArrayList<Offset>()

    val step = if ((angle / 12).toInt() > 1) (angle / 12).toInt() else 1

    for (i in 0..angle.toInt() step step) {
        val x = radius * cos(Math.toRadians(i.toDouble() - 90)) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble() - 90)) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}

fun generatePointsForSpinnerEnd2(radius: Int, origin: Offset, angle: Float): ArrayList<Offset> {
    val list = ArrayList<Offset>()

    val step = if (((360 - angle) / 12).toInt() > 1) ((360 - angle) / 12).toInt() else 1
    val lowerLimit = ceil(angle / 12.toFloat()) * 12

    for (i in lowerLimit.toInt()..360 step step) {
        val x = radius * cos(Math.toRadians(i.toDouble() - 90)) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble() - 90)) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}