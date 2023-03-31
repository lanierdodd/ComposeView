package com.example.composeview.touch

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/30 10:36
 * Desc  :
 */
@Composable
fun TestTouch1() {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var offset by remember {
        mutableStateOf(0f)
    }
    Column {
        Text(
            text = "Smurf",
            modifier = Modifier
                .size(60.dp)
                .offset {
                    IntOffset(x = offset.roundToInt(), 0)
                }
                .draggable(
                    rememberDraggableState(
                        onDelta = {
                            offset += it
                        }
                    ),
                    orientation = Orientation.Horizontal,
                    interactionSource = interactionSource,
                    onDragStarted = {
                        println("start $it")
                    },
                    onDragStopped = {
                        println("stop $it")
                    }
                )
        )
        val dragState by interactionSource.collectIsDraggedAsState()
        Text(text = if (dragState) "在拖动" else "在静止")
    }
}

@Composable
fun TestTouch2() {
    Column {
        Text(
            text = "Smurf",
            modifier = Modifier
                .scrollable(
                    rememberScrollableState(
                        consumeScrollDelta = {
                            println("滚动了 $it")
                            it
                        }
                    ),
                    orientation = Orientation.Horizontal
                )
        )
    }
}

@Composable
fun TestTouchEg1() {
    var offsetY by remember {
        mutableStateOf(-200f)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(120.dp)
                .offset {
                    IntOffset(0, y = offsetY.roundToInt())
                }
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(Color.Red)
                .align(Alignment.CenterHorizontally)
                .draggable(
                    rememberDraggableState(
                        onDelta = {
                            val y = offsetY.toInt()
                            if (y >= -200 && y <= -10) {
                                val m = offsetY
                                when {
                                    m + it > -10f -> offsetY = -10f
                                    m + it < -200f -> offsetY = -200f
                                    else -> offsetY += it
                                }
                            }
                        }
                    ),
                    orientation = Orientation.Vertical
                )
        )
    }
}

@Composable
fun TestTouch3() {
    var offset by remember {
        mutableStateOf(0f)
    }
    val dispatcher = remember {
        NestedScrollDispatcher()
    }
    val connection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                offset += available.y
                return available
            }
        }
    }
    Column(
        modifier = Modifier
            .offset {
                IntOffset(0, y = offset.roundToInt())
            }
            .draggable(
                rememberDraggableState(
                    onDelta = {
                        val consumed =
                            dispatcher.dispatchPreScroll(Offset(0f, it), NestedScrollSource.Drag)
                        offset += it - consumed.y
                        dispatcher.dispatchPostScroll(
                            Offset(0f, it),
                            Offset.Zero,
                            NestedScrollSource.Drag
                        )
                    }
                ),
                orientation = Orientation.Vertical
            )
            .nestedScroll(connection, dispatcher)
    ) {
        repeat(10) {
            Text(text = "index >> $it")
        }
        LazyColumn(
            modifier = Modifier
                .height(50.dp)
        ) {
            items(5) {
                Text(text = "inner tv $it")
            }
        }
    }
}

@Composable
fun TestTouch4() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "lalalala",
            modifier = Modifier
                .background(Color.Yellow)
                .mxClick {
                    println("click event")
                }
        )
    }
}

@Composable
fun Modifier.mxClick(
    onClick: () -> Unit
) = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            while (true) {
                val event = awaitPointerEvent()
                if (event.type == PointerEventType.Move) {
                    val pos = event.changes[0].position
                    if (pos.x < 0 || pos.x > size.width || pos.y < 0 || pos.y > size.height) {
                        println("aaaa")
                        break
                    } else {
                        println("bbbb")
                    }
                } else if (event.type == PointerEventType.Release && event.changes.size == 1) {
                    onClick.invoke()
                    break
                }
            }
        }
    }
}
