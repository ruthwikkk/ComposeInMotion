package com.ruthwikkk.compose.dots.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate

@Composable
fun ShapesLoader() {

    val side = 100f
    val radiusSquare1 = rememberInfiniteTransition()
    val radiusAnimSquare1 by radiusSquare1.animateFloat(
        initialValue = 150f,
        targetValue = 55f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 500,
                delayMillis = 500,
                easing = FastOutSlowInEasing
            ),
            RepeatMode.Reverse
        )
    )

    val curveRadiusSquare1 = rememberInfiniteTransition()
    val curveRadiusAnimSquare1 by curveRadiusSquare1.animateFloat(
        initialValue = 43f,
        targetValue = 75f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 500,
                delayMillis = 500,
                easing = FastOutLinearInEasing
            ),
            RepeatMode.Reverse
        )
    )

    val rotateTriangle = rememberInfiniteTransition()
    val rotateAnimTriangle by rotateTriangle.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1000,
                delayMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            RepeatMode.Restart
        )
    )

    val translateCircle = rememberInfiniteTransition()
    val translateAnimCircle by translateCircle.animateFloat(
        initialValue = 0f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 500,
                delayMillis = 500,
                easing = FastOutSlowInEasing
            ),
            RepeatMode.Reverse
        )
    )

    val sizeSquare2 = rememberInfiniteTransition()
    val sideAnimSquare by sizeSquare2.animateFloat(
        initialValue = 0f,
        targetValue = 43f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 500,
                delayMillis = 500,
                easing = FastOutSlowInEasing
            ),
            RepeatMode.Reverse
        )
    )


    Canvas(modifier = Modifier.fillMaxSize()) {

        val centreForRect1 = Offset(center.x - side, center.y - side)

        val centreForCircle = Offset(center.x + side, center.y - side)

        val centreForTriangle = Offset(center.x - side + 20, center.y + side)
        val rectForTriangle = Rect(centreForTriangle, side)
        val trianglePath = Path().apply {
            moveTo(rectForTriangle.topLeft)
            lineTo(Offset(rectForTriangle.centerRight.x - 20, rectForTriangle.centerRight.y))
            lineTo(rectForTriangle.bottomLeft)
            // note that two more point repeats needed to round all corners
            lineTo(rectForTriangle.topLeft)
            lineTo(Offset(rectForTriangle.centerRight.x - 20, rectForTriangle.centerRight.y))
        }

        val centreForRect2 = Offset(center.x + side, center.y + side)

        drawIntoCanvas { canvas ->
            drawRoundRect(
                Color(0XFF457BD7),
                getCentreForRectangle(centreForRect1.x, centreForRect1.y, radiusAnimSquare1),
                size = Size(radiusAnimSquare1, radiusAnimSquare1),
                cornerRadius = CornerRadius(curveRadiusAnimSquare1, curveRadiusAnimSquare1)
            )

            drawCircle(
                Color(0XFFFF5928),
                center = Offset(centreForCircle.x - 20, centreForCircle.y + translateAnimCircle),
                radius = side - 20
            )

            drawRoundRect(
                Color(0XFF00776B),
                getCentreForRectangle(centreForRect2.x - 20, centreForRect2.y + sideAnimSquare, 150f),
                size = Size(150f, 150f - sideAnimSquare),
                cornerRadius = CornerRadius(43f, 43f)
            )

            rotate(
                rotateAnimTriangle,
                Offset(centreForTriangle.x - side / 3, centreForTriangle.y)
            ) {
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        color = Color(0XFFFFCB00)
                        pathEffect = PathEffect.cornerPathEffect(rectForTriangle.maxDimension / 3)
                    }
                )
            }
        }
    }


}

fun getCentreForRectangle(x: Float, y: Float, side: Float): Offset {
    val xx = x - side / 2
    val yy = y - side / 2
    return Offset(xx, yy)
}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)