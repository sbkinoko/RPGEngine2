package layout.controller

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import domain.controller.StickPosition
import extension.pxToDp
import values.Colors

@Composable
fun Stick(
    modifier: Modifier = Modifier,
) {
    //todo largeとsmallもstickにしまいたい
    var largeCircleSize: Int by remember { mutableStateOf(0) }
    var smallCircleSize: Int by remember { mutableStateOf(0) }

    var stickPosition: StickPosition by remember {
        mutableStateOf(
            StickPosition(
                circleSize = 1,
                stickSize = 1,
            )
        )
    }

    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize()
            .onGloballyPositioned {
                largeCircleSize = it.size.height
                smallCircleSize = it.size.height / 3
                stickPosition = StickPosition(
                    circleSize = largeCircleSize / 2,
                    stickSize = smallCircleSize / 2,
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(size = largeCircleSize.pxToDp())
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        stickPosition = StickPosition(
                            circleSize = largeCircleSize / 2,
                            stickSize = smallCircleSize / 2,
                            position = down.position,
                        )

                        do {
                            val event = awaitPointerEvent()
                            val lastPosition = event.changes.last().position
                            stickPosition = StickPosition(
                                circleSize = largeCircleSize / 2,
                                stickSize = smallCircleSize / 2,
                                position = lastPosition,
                            )
                        } while (
                            event.changes.fastAny { it.pressed }
                        )

                        stickPosition = StickPosition(
                            circleSize = largeCircleSize / 2,
                            stickSize = smallCircleSize / 2,
                        )
                    }
                },
            onDraw = {
                drawCircle(
                    color = Colors.ControllerLine,
                    style = Stroke(width = 5f),
                )
            }
        )

        Canvas(
            modifier = Modifier
                .offset(
                    x = stickPosition.x.pxToDp(),
                    y = stickPosition.y.pxToDp(),
                )
                .size(
                    size = smallCircleSize.pxToDp()
                ),
            onDraw = {
                drawCircle(
                    color = Colors.ControllerLine,
                    style = Stroke(width = 5f),
                )
            }
        )
    }
}
