package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastAny
import common.extension.pxToDp
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import values.Colors
import values.EventType

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

    val dir = mapViewModel.dirFlow.collectAsState()
    val square = mapViewModel
        .playerSquare
        .collectAsState(
    )
    val eventSquare = mapViewModel.eventSquareFlow.collectAsState()
    val eventType = mapViewModel.eventTypeFlow.collectAsState()

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
            modifier = Modifier
                .offset(
                    x = (square.value.x * screenRatio).pxToDp(),
                    y = (square.value.y * screenRatio).pxToDp(),
                )
                .size((square.value.size * screenRatio).pxToDp())
                .background(Colors.Player)
                .clickable {
                    mapViewModel.touchCharacter()
                },
            dir = dir.value,
        )

        Spacer(
            modifier = Modifier
                .offset(
                    x = (eventSquare.value.x * screenRatio).pxToDp(),
                    y = (eventSquare.value.y * screenRatio).pxToDp(),
                )
                .size((eventSquare.value.size * screenRatio).pxToDp())
                .background(
                    if (eventType.value == EventType.None) {
                        Colors.NotEventCollision
                    } else {
                        Colors.CanEventCollision
                    }
                )
                .clickable {
                    mapViewModel.touchEventSquare()
                },
        )
    }
}
