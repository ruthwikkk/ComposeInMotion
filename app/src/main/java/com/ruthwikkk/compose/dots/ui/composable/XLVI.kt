package com.ruthwikkk.compose.dots.ui.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import com.ruthwikkk.compose.dots.ui.theme.Purple500

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