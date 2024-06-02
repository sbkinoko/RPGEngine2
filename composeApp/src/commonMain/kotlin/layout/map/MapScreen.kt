package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastAny
import extension.pxToDp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import values.Colors
import viewmodel.MapViewModel

@Composable
@Preview
fun MapScreen(
    mapViewModel: MapViewModel,
    screenSize: Int
) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(30L)
            mapViewModel.updatePosition()
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier
                .size(size = screenSize.pxToDp())
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        mapViewModel.setTapPoint(
                            x = down.position.x,
                            y = down.position.y,
                        )
                        do {
                            val event = awaitPointerEvent()
                            val lastPosition = event.changes.last().position
                            mapViewModel.setTapPoint(
                                x = lastPosition.x,
                                y = lastPosition.y,
                            )
                        } while (
                            event.changes.fastAny { it.pressed }
                        )
                        mapViewModel.resetTapPoint()
                    }
                }
                .background(
                    color = Colors.MapBackground
                ),
        ) {
            mapViewModel.backgroundManger.collectAsState().value?.let {
                showBackground(it)
            }

            Player(
                square = mapViewModel.playerPosition.collectAsState().value,
            )
        }
    }
}
