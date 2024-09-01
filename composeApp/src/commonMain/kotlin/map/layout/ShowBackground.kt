package map.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import common.values.Colors
import map.domain.BackgroundCell
import map.viewmodel.MapViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Background(
    mapViewModel: MapViewModel,
    screenRatio: Float,
) {
    val backgroundCell: List<List<BackgroundCell>> = mapViewModel.backgroundCells.collectAsState(
        listOf()
    ).value

    val imageBinder = ImageBinder()

    Box {
        backgroundCell.forEachIndexed { row, backgroundCells ->
            backgroundCells.forEachIndexed { col, cell ->
                cell.apply {
                    val aroundCellId = mapViewModel.getAroundCellId(
                        x = mapPoint.x,
                        y = mapPoint.y,
                    )

                    Box(
                        modifier = Modifier
                            .size(
                                (cellSize * screenRatio).pxToDp()
                            )
                            .offset(
                                x = (square.leftSide * screenRatio).pxToDp(),
                                y = (square.topSide * screenRatio).pxToDp(),
                            )
                            .border(
                                width = 1.dp,
                                color = if (isPlayerIncludeCell) {
                                    Colors.PlayerIncludeCell
                                } else {
                                    Colors.BackgroundCell
                                },
                                shape = RectangleShape,
                            )
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(
                                imageBinder.bind(
                                    aroundCellId = aroundCellId,
                                )
                            ),
                            contentDescription = "background"
                        )

                        collisionList.forEach {
                            Canvas(
                                modifier = Modifier.fillMaxSize(),
                                onDraw = {
                                    drawPath(
                                        path = it.toPath(screenRatio),
                                        color = Colors.BackgroundCell,
                                    )
                                }
                            )
                        }

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
