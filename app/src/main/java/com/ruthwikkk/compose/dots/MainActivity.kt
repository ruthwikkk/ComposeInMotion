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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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
                    Spinner()
                }
            }
        }
    }
}

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

    val lowerLimit = ceil(angle/20.0)*20
    for (i in lowerLimit.toInt()..360 step 20) {
        val x = radius * cos(Math.toRadians(i.toDouble())) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble())) + origin.y
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
                            drawRect(Color.White, twoDArray[i][j], size = Size(translateAnim, translateAnim))
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

enum class RectState {
    INIT, RIGHT_EXPAND, RIGHT_COLLAPSE, BOTTOM_EXPAND, BOTTOM_COLLAPSE, LEFT_EXPAND, LEFT_COLLAPSE, TOP_EXPAND, TOP_COLLAPSE
}

@Composable
fun XLVI() {

    val animationDuration = 400
    val side = with(LocalDensity.current) { 60.dp.toPx() }
    val spacing = with(LocalDensity.current) { 45.dp.toPx() }
    val originX = 0f
    val originY = 0f

    var state by remember {
        mutableStateOf(RectState.INIT)
    }

    var sizeTarget by remember {
        mutableStateOf(2 * side + spacing)
    }

    var positionTarget by remember {
        mutableStateOf(originX)
    }

    fun stepForward() {
        when (state) {
            RectState.INIT -> {
                state = RectState.LEFT_COLLAPSE
                sizeTarget = side
                positionTarget = originX
            }
            RectState.LEFT_COLLAPSE -> {
                state = RectState.BOTTOM_EXPAND
                sizeTarget = 2 * side + spacing
                positionTarget = originY
            }
            RectState.BOTTOM_EXPAND -> {
                state = RectState.BOTTOM_COLLAPSE
                sizeTarget = side
                positionTarget = originY + spacing + side
            }
            RectState.BOTTOM_COLLAPSE -> {
                state = RectState.RIGHT_EXPAND
                sizeTarget = 2 * side + spacing
                positionTarget = originX
            }
            RectState.RIGHT_EXPAND -> {
                state = RectState.RIGHT_COLLAPSE
                sizeTarget = side
                positionTarget = originX + spacing + side
            }
            RectState.RIGHT_COLLAPSE -> {
                state = RectState.TOP_EXPAND
                sizeTarget = 2 * side + spacing
                positionTarget = originY
            }
            RectState.TOP_EXPAND -> {
                state = RectState.TOP_COLLAPSE
                sizeTarget = side
                positionTarget = originX + side + spacing
            }
            RectState.TOP_COLLAPSE -> {
                state = RectState.LEFT_EXPAND
                sizeTarget = 2 * side + spacing
                positionTarget = originX
            }
            RectState.LEFT_EXPAND -> {
                state = RectState.LEFT_COLLAPSE
                sizeTarget = side
                positionTarget = originX
            }
        }
    }

    val sizeAnimation: Float by animateFloatAsState(
        targetValue = sizeTarget,
        animationSpec = tween(animationDuration), finishedListener = {
            stepForward()
        }
    )

    val positionAnimation: Float by animateFloatAsState(
        targetValue = positionTarget,
        animationSpec = tween(animationDuration)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Purple500)){
        Canvas(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        stepForward()
                    })
                }
        ) {
            val sizeArray = when (state) {
                RectState.INIT -> listOf(
                    Size(side, side),
                    Size(side, side),
                    Size(2 * side + spacing, side)
                )
                RectState.LEFT_COLLAPSE -> listOf(
                    Size(side, side),
                    Size(side, side),
                    Size(sizeAnimation, side)
                )
                RectState.BOTTOM_EXPAND -> listOf(
                    Size(side, side),
                    Size(side, sizeAnimation),
                    Size(side, side)
                )
                RectState.BOTTOM_COLLAPSE -> listOf(
                    Size(side, side),
                    Size(side, sizeAnimation),
                    Size(side, side)
                )
                RectState.RIGHT_EXPAND -> listOf(
                    Size(sizeAnimation, side),
                    Size(side, side),
                    Size(side, side)
                )
                RectState.RIGHT_COLLAPSE -> listOf(
                    Size(sizeAnimation, side),
                    Size(side, side),
                    Size(side, side)
                )
                RectState.TOP_EXPAND, RectState.TOP_COLLAPSE -> listOf(
                    Size(side, side),
                    Size(side, side),
                    Size(side, sizeAnimation)
                )
                RectState.LEFT_EXPAND -> listOf(
                    Size(side, side),
                    Size(sizeAnimation, side),
                    Size(side, side)
                )
            }
            val startArray = when (state) {
                RectState.INIT -> listOf(
                    Offset(originX, originY),
                    Offset(originX + side + spacing, originY),
                    Offset(originX, originY + side + spacing)
                )
                RectState.LEFT_COLLAPSE -> listOf(
                    Offset(originX, originY),
                    Offset(originX + side + spacing, originY),
                    Offset(originX, originY + side + spacing)
                )
                RectState.BOTTOM_EXPAND -> listOf(
                    Offset(originX, originY),
                    Offset(originX + side + spacing, originY),
                    Offset(originX, originY + side + spacing)
                )
                RectState.BOTTOM_COLLAPSE -> listOf(
                    Offset(originX, originY),
                    Offset(originX + side + spacing, positionAnimation),
                    Offset(originX, originY + side + spacing)
                )
                RectState.RIGHT_EXPAND -> listOf(
                    Offset(originX, originY),
                    Offset(originX + side + spacing, originY + side + spacing),
                    Offset(originX, originY + side + spacing)
                )
                RectState.RIGHT_COLLAPSE -> listOf(
                    Offset(positionAnimation, originY),
                    Offset(originX + side + spacing, originY + side + spacing),
                    Offset(originX, originY + side + spacing)
                )
                RectState.TOP_EXPAND -> listOf(
                    Offset(originX + side + spacing, originY),
                    Offset(originX + side + spacing, originY + side + spacing),
                    Offset(originX, positionAnimation)
                )
                RectState.TOP_COLLAPSE -> listOf(
                    Offset(originX + side + spacing, originY),
                    Offset(originX + side + spacing, originY + side + spacing),
                    Offset(originX, originY)
                )
                RectState.LEFT_EXPAND -> listOf(
                    Offset(originX + side + spacing, originY),
                    Offset(positionAnimation, originY + side + spacing),
                    Offset(originX, originY)
                )
            }

            sizeArray.zip(startArray).forEach { pair ->
                drawRect(Color.White, pair.second, size = pair.first, style = Stroke(width = side/2))
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

@Composable
fun Dots() {
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

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF212121))
        ) {
            drawCircle(color = Color.Cyan, center = this.center, radius = 10f)

            generatePoints(radiusAnimOuter1.toInt(), this.center, translateAnim).forEach {
                drawCircle(color = Color.Cyan, center = it, radius = 10f)
            }

            generatePoints(radiusAnimOuter2.toInt(), this.center, translateAnim + 15).forEach {
                drawCircle(color = Color.Cyan, center = it, radius = 10f)
            }

            generatePoints(radiusAnimInner1.toInt(), this.center, translateAnim).forEach {
                drawCircle(color = Color.Cyan, center = it, radius = 2f)
            }

            generatePoints(radiusAnimInner2.toInt(), this.center, translateAnim + 15).forEach {
                drawCircle(color = Color.Cyan, center = it, radius = 2f)
            }
        }
    }
}

fun generatePoints(radius: Int, origin: Offset, startAngle: Float): ArrayList<Offset> {
    val list = ArrayList<Offset>()

    for (i in (0 + startAngle.toInt())..(360 + startAngle.toInt()) step 30) {
        val x = radius * cos(Math.toRadians(i.toDouble())) + origin.x
        val y = radius * sin(Math.toRadians(i.toDouble())) + origin.y
        list.add(Offset(x.toFloat(), y.toFloat()))
    }
    return list
}