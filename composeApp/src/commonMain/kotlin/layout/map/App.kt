package layout.map

import androidx.compose.foundation.Image
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
import manager.map.BackgroundManager
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import values.Colors
import viewmodel.MapViewModel

@Composable
@Preview
fun App() {
    val mapViewModel: MapViewModel by remember {
        mutableStateOf(
            MapViewModel(
                playerSize = 100f,
            )
        )
    }

    mapViewModel.updatePosition()
    val backgroundCellManager = mapViewModel.backgroundCellManger.collectAsState()
    val playerSquare = mapViewModel.playerPosition.collectAsState()
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
                square = playerSquare.value,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun showBackground(backgroundManager: BackgroundManager) {
    val imageBinder = ImageBinder()

    Box {
        for (row: Int in 0 until backgroundManager.allCellNum) {
            for (col: Int in 0 until backgroundManager.allCellNum) {
                backgroundManager.getCell(
                    col = col,
                    row = row,
                ).apply {
                    Box(
                        modifier = Modifier
                            .size(
                                this.cellSize.pxToDp()
                            )
                            .offset(
                                x = this.square.leftSide.pxToDp(),
                                y = this.square.topSide.pxToDp(),
                            )
                            .border(
                                width = 1.dp,
                                color = Colors.BackgroundCell,
                                shape = RectangleShape,
                            )
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(
                                imageBinder.bind(imgId = imgID)
                            ),
                            contentDescription = "background"
                        )


                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = StringBuilder()
                                .append("row:$row\n")
                                .append("col:$col\n")
                                .append("mapX:${mapPoint.x}\n")
                                .append("mapY:${mapPoint.y}\n")
                                .toString(),
                        )
                    }
                }
            }
        }
    }
}
