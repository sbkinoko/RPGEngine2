package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Press
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Release
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import extension.pxToDp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var x: Float by remember { mutableStateOf(0f) }
    var y: Float by remember { mutableStateOf(0f) }

    val mapViewModel = MapViewModel()

    mapViewModel.updatePosition()
    val playerPosition = mapViewModel.playerPosition.collectAsState()

    var screenSize: Int by remember { mutableStateOf(0) }
    MaterialTheme {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, _ ->
                            mapViewModel.updateVelocity(
                                x = change.position.x,
                                y = change.position.y,
                            )
                        },
                    )
                    detectTapGestures(
                        onTap = {
                            mapViewModel.updateVelocity(
                                x = it.x,
                                y = it.y,
                            )
                        },
                        onPress = {
                            mapViewModel.updateVelocity(
                                x = it.x,
                                y = it.y,
                            )
                        }
                    )
                    awaitEachGesture {
                        val event = awaitPointerEvent()
                        when (val type: PointerEventType = event.type) {
                            Press -> {

                            }

                            Release -> {
                                mapViewModel.resetVelocity()
                            }
                        }
                    }
                }
                .onGloballyPositioned {
                    screenSize = it.size.width
                }
                .then(
                    if (screenSize != 0) {
                        Modifier.size(screenSize.pxToDp())
                    } else {
                        Modifier.fillMaxSize()
                    }
                )
                .background(
                    color = values.Colors.MapBackground
                ),
        ) {
            Player(
                x = playerPosition.value.x,
                y = playerPosition.value.y,
                size = mapViewModel.getPlayerSize(),
            )
        }
    }
}
