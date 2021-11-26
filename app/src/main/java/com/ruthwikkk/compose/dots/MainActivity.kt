package com.ruthwikkk.compose.dots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.ruthwikkk.compose.dots.ui.theme.DotsTheme
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Helix()
                }
            }
        }
    }
}

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
                    (320 + i.toFloat() * 2f),
                    this.center.y + sin(Math.toRadians((i + translateAnim).toDouble())).toFloat() * yScale
                )
            )

            drawCircle(
                color = Color(0xFF393939),
                radius = radius - (cos(Math.toRadians((i + 180 - translateAnim).toDouble())) * radius / 2).toFloat(),
                center = Offset(
                    (320 + i.toFloat() * 2f),
                    this.center.y + sin(-Math.toRadians((i + translateAnim).toDouble())).toFloat() * yScale
                )
            )
        }
    }
}

@Composable
fun Dots(){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val transition = rememberInfiniteTransition()
        val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 5000,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                RepeatMode.Restart
            )
        )

        val radiusTransitionOuter1 = rememberInfiniteTransition()
        val radiusAnimOuter1 by radiusTransitionOuter1.animateFloat(
            initialValue = 110f,
            targetValue = 150f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                RepeatMode.Reverse
            )
        )

        val radiusTransitionOuter2 = rememberInfiniteTransition()
        val radiusAnimOuter2 by radiusTransitionOuter2.animateFloat(
            initialValue = 150f,
            targetValue = 110f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                RepeatMode.Reverse
            )
        )

        val radiusTransitionInner1 = rememberInfiniteTransition()
        val radiusAnimInner1 by radiusTransitionInner1.animateFloat(
            initialValue = 60f,
            targetValue = 80f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                RepeatMode.Reverse
            )
        )

        val radiusTransitionInner2 = rememberInfiniteTransition()
        val radiusAnimInner2 by radiusTransitionInner2.animateFloat(
            initialValue = 80f,
            targetValue = 60f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                RepeatMode.Reverse
            )
        )

        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF212121))){
            drawCircle(color = Color.Cyan, center = this.center, radius = 10f)

            generatePoints(radiusAnimOuter1.toInt(), this.center, translateAnim).forEach{
                drawCircle(color = Color.Cyan, center = it, radius = 10f)
            }

            generatePoints(radiusAnimOuter2.toInt(), this.center, translateAnim + 15).forEach{
                drawCircle(color = Color.Cyan, center = it, radius = 10f)
            }

            generatePoints(radiusAnimInner1.toInt(), this.center, translateAnim).forEach{
                drawCircle(color = Color.Cyan, center = it, radius = 2f)
            }

            generatePoints(radiusAnimInner2.toInt(), this.center, translateAnim + 15).forEach{
                drawCircle(color = Color.Cyan, center = it, radius = 2f)
            }
        }
    }
}

fun generatePoints(radius: Int, origin: Offset, startAngle: Float): ArrayList<Offset>{
    val list = ArrayList<Offset>()

    for(i in (0 + startAngle.toInt())..(360 + startAngle.toInt()) step 30){
        val x = radius * cos(Math.toRadians(i.toDouble())) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble())) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}