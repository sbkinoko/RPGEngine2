package controller.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import common.extension.pxToDp
import common.values.Colors
import controller.domain.ControllerCallback
import controller.domain.Stick
import kotlinx.coroutines.delay

@Composable
fun Stick(
    controllerCallback: ControllerCallback,
    modifier: Modifier = Modifier,
) {
    var stick: Stick by remember {
        mutableStateOf(
            Stick(
                areaRadius = 1,
                stickRadius = 1,
            )
        )
    }

    LaunchedEffect(key1 = controllerCallback) {
        while (true) {
            // スティックを放している場合
            if (stick.isReleased) {
                //　スティックをリセット
                controllerCallback.moveStick(
                    stick = stick,
                )

                //　スティックを触るまで待機
                while (stick.isReleased) {
                    delay(1)
                }
            }

            //　スティックを触っているので呼び出し
            controllerCallback.moveStick(
                stick = stick,
            )
            delay(30)
        }
    }

    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize()
            .onGloballyPositioned {
                // 初回配置なのでスティックを新規生成
                stick = Stick(
                    areaRadius = it.size.height / 2,
                    stickRadius = it.size.height / 6,
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(size = (stick.areaRadius * 2).pxToDp())
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        stick = stick.copy(
                            position = down.position,
                        )

                        do {
                            val event = awaitPointerEvent()
                            val lastPosition = event.changes.last().position
                            stick = stick.copy(
                                position = lastPosition,
                            )
                        } while (
                            event.changes.fastAny { it.pressed }
                        )

                        stick = stick.copy(
                            position = stick.center,
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
                    x = stick.x.pxToDp(),
                    y = stick.y.pxToDp(),
                )
                .size(
                    size = (stick.stickRadius * 2).pxToDp(),
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
