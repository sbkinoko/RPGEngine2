package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.util.fastAny
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
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        mapViewModel.updateVelocity(
                            x = down.position.x,
                            y = down.position.y,
                        )
                        do {
                            val event = awaitPointerEvent()
                            val lastPosition = event.changes.last().position
                            mapViewModel.updateVelocity(
                                x = lastPosition.x,
                                y = lastPosition.y,
                            )
                        } while (
                            event.changes.fastAny { it.pressed }
                        )
                        mapViewModel.resetVelocity()
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
