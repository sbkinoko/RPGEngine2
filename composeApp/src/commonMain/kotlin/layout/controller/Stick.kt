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
import extension.pxToDp
import values.Colors

@Composable
fun Stick(
    modifier: Modifier = Modifier,
) {
    var largeCircleSize: Int by remember { mutableStateOf(0) }
    var smallCircleSize: Int by remember { mutableStateOf(0) }

    var tapX: Float by remember { mutableStateOf(0f) }
    var tapY: Float by remember { mutableStateOf(0f) }
    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    largeCircleSize = it.size.height
                    smallCircleSize = it.size.height / 3
                }
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        tapX = down.position.x - largeCircleSize / 2f
                        tapY = down.position.y - largeCircleSize / 2f
                        do {
                            val event = awaitPointerEvent()
                            val lastPosition = event.changes.last().position
                            tapX = lastPosition.x - largeCircleSize / 2f
                            tapY = lastPosition.y - largeCircleSize / 2f
                        } while (
                            event.changes.fastAny { it.pressed }
                        )
                        tapX = 0f
                        tapY = 0f
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
                    x = tapX.pxToDp(),
                    y = tapY.pxToDp(),
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
