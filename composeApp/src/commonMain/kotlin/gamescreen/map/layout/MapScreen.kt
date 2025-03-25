package gamescreen.map.layout

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastAny
import gamescreen.map.layout.background.Background
import gamescreen.map.layout.npc.NPC
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import values.GameParams

@Composable
@Preview
fun MapScreen(
    screenRatio: Float,
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = koinInject(),
) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(GameParams.DELAY)
            mapViewModel.updatePosition()
        }
    }

    val mapUiState by mapViewModel
        .uiStateFlow
        .collectAsState()

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

        NPC(
            npcData = mapUiState.npcData,
            screenRatio = screenRatio,
        )

        Player(
            player = mapUiState.player,
            clickPlayer = {
                mapViewModel.touchCharacter()
            },
            clickEventSquare = {
                mapViewModel.touchEventSquare()
            },
            screenRatio = screenRatio,
        )
    }
}
