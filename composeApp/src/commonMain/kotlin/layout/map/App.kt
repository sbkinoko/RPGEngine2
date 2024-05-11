package layout.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import extension.pxToDp
import manager.map.BackgroundCellManager
import org.jetbrains.compose.ui.tooling.preview.Preview
import values.Colors

@Composable
@Preview
fun App() {
    val mapViewModel: MapViewModel by remember { mutableStateOf(MapViewModel()) }

    mapViewModel.updatePosition()
    val playerPosition = mapViewModel.playerPosition.collectAsState()
    val backgroundCellManager = mapViewModel.backgroundCellManger.collectAsState()

    var screenSize: Int by remember { mutableStateOf(0) }
    MaterialTheme {
        Box(
            modifier = Modifier
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
                        mapViewModel.resetVelocity()
                    }
                }
                .onGloballyPositioned {
                    screenSize = it.size.width
                    mapViewModel.initBackgroundCellManager(screenSize)
                }
                .then(
                    if (screenSize != 0) {
                        Modifier.size(screenSize.pxToDp())
                    } else {
                        Modifier.fillMaxSize()
                    }
                )
                .background(
                    color = Colors.MapBackground
                ),
        ) {
            if (backgroundCellManager.value != null) {
                showBackground(backgroundCellManager.value!!)
            }

            Player(
                x = playerPosition.value.x,
                y = playerPosition.value.y,
                size = mapViewModel.getPlayerSize(),
            )
        }
    }
}

@Composable
fun showBackground(backgroundCellManager: BackgroundCellManager) {
    Box {
        for (row: Int in 0 until backgroundCellManager.cellNum) {
            for (col: Int in 0 until backgroundCellManager.cellNum) {
                backgroundCellManager.getCell(
                    col = col,
                    row = row,
                ).apply {
                    Text(
                        modifier = Modifier
                            .size(
                                this.cellSize.pxToDp()
                            )
                            .offset(
                                x = this.displayPoint.x.pxToDp(),
                                y = this.displayPoint.y.pxToDp(),
                            )
                            .border(
                                width = 1.dp,
                                color = Colors.BackgroundCell,
                                shape = RectangleShape,
                            ),
                        text = "row:$row\ncol:$col\nx=${this.displayPoint.x}",
                    )
                }
            }
        }
    }
}
