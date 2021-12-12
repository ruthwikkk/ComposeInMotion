package com.ruthwikkk.compose.dots

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ruthwikkk.compose.dots.ui.composable.Dots
import com.ruthwikkk.compose.dots.ui.composable.Helix
import com.ruthwikkk.compose.dots.ui.composable.XLVI
import com.ruthwikkk.compose.dots.ui.theme.DotsTheme
import com.ruthwikkk.compose.dots.ui.theme.Purple500
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    // Dots()
                    // Helix()
                    // XLVI()
                    // Square()
                    Spinner2()
                }
            }
        }
    }
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

@Composable
fun Square() {
    var squareSize = 4
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 20f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000,
                delayMillis = 0,
                easing = LinearEasing
            ),
            RepeatMode.Restart,
        )
    )

    var step = 1

    var twoDArray = arrayOf<Array<Offset>>()
    for (i in 0 until squareSize) {
        var array = arrayOf<Offset>()
        for (j in 0 until squareSize) {
            array += Offset(i * 50f, j * 50f)
        }
        twoDArray += array
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple500)
    ) {
        Canvas(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        step++
                    })
                }
        ) {
            /*for (array in twoDArray) {
                for (value in array) {
                    drawRect(Color.White, value, size = Size(translateAnim, translateAnim))
                }
            }*/

            /*for (i in 0..4) {
                for (j in 0..4) {
                    if(i == j){
                        drawRect(Color.White, twoDArray[i][j], size = Size(translateAnim, translateAnim))
                    }else{
                        drawRect(Color.White, twoDArray[i][j], size = Size(20f, 20f))
                    }
                }
            }*/

            for (i in 0 until squareSize) {
                for (j in 0 until squareSize) {
                    when {
                        i + j == step -> {
                            drawRect(
                                Color.White,
                                twoDArray[i][j],
                                size = Size(translateAnim, translateAnim)
                            )
                        }
                        i + j < step -> {
                            drawRect(Color.White, twoDArray[i][j], size = Size(40f, 40f))
                        }
                        else -> {
                            drawRect(Color.White, twoDArray[i][j], size = Size(20f, 20f))
                        }
                    }
                }
            }
        }
    }
}

