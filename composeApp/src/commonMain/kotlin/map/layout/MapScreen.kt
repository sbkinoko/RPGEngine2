package map.layout

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastAny
import kotlinx.coroutines.delay
import map.domain.collision.Square
import map.viewmodel.MapViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MapScreen(
    mapViewModel: MapViewModel,
    screenRatio: Float,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(30L)
            mapViewModel.updatePosition()
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    mapViewModel.setTapPoint(
                        x = down.position.x / screenRatio,
                        y = down.position.y / screenRatio,
                    )
                    do {
                        val event = awaitPointerEvent()
                        val lastPosition = event.changes.last().position
                        mapViewModel.setTapPoint(
                            x = lastPosition.x / screenRatio,
                            y = lastPosition.y / screenRatio,
                        )
                    } while (
                        event.changes.fastAny { it.pressed }
                    )
                    mapViewModel.resetTapPoint()
                }
            },
    ) {
        mapViewModel.backgroundManger.collectAsState().value.let {
            showBackground(
                backgroundManager = it,
                screenRatio = screenRatio
            )
        }

        Player(
            square = mapViewModel.playerSquare.collectAsState(
                Square(
                    x = 0f,
                    y = 0f,
                    size = 0f,
                )
            ).value,
            screenRatio = screenRatio
        )
    }
}
