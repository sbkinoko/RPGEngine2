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
import map.repository.player.PlayerRepository
import map.viewmodel.MapViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun MapScreen(
    screenRatio: Float,
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = koinInject(),
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
        Background(
            mapViewModel = mapViewModel,
            screenRatio = screenRatio
        )

        Player(
            square = mapViewModel.playerSquare.collectAsState(
                PlayerRepository.initialSquare
            ).value,
            screenRatio = screenRatio
        )
    }
}