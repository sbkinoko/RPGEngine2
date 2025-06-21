package gamescreen.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastAny
import gamescreen.map.layout.background.Background
import gamescreen.map.layout.background.DebugInfo
import gamescreen.map.layout.background.MapClip
import gamescreen.map.layout.background.ObjectData
import gamescreen.map.layout.npc.NPC
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import values.Colors
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

    val fps by mapViewModel
        .fpsFlow
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
            screenRatio = screenRatio,
            backgroundCell = mapUiState.backgroundData,
        )

        NPC(
            npcData = mapUiState.npcData,
            screenRatio = screenRatio,
        )

        ObjectData(
            screenRatio = screenRatio,
            objectData = mapUiState.backObjectData,
            modifier = Modifier.fillMaxSize()
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

        var width by remember {
            mutableStateOf(0)
        }
        var height by remember {
            mutableStateOf(0)
        }

        ObjectData(
            screenRatio = screenRatio,
            objectData = mapUiState.frontObjectData,
            modifier =
                Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        width = it.size.width
                        height = it.size.height
                    }
                    .clip(
                        shape = MapClip(
                            width = width.toFloat(),
                            height = height.toFloat(),
                            player = mapUiState.player,
                            screenRatio = screenRatio,
                        ),
                    )
        )

        DebugInfo(
            screenRatio = screenRatio,
            backgroundCell = mapUiState.backgroundData,
            eventCell = mapUiState.playerIncludeCell,
        )

        Text(
            modifier = Modifier
                .align(
                    Alignment.BottomEnd,
                ).background(
                    Colors.FpsBackground
                ).padding(10.dp),
            text = "FPS:${fps}",
            fontSize = 50.sp,
        )
    }
}
