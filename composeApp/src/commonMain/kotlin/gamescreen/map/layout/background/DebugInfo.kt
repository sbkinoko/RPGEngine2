package gamescreen.map.layout.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.menu.debug.DebugViewModel
import org.koin.compose.koinInject
import values.Colors

@Composable
fun DebugInfo(
    debugViewModel: DebugViewModel = koinInject(),
    backgroundCell: BackgroundData,
    eventCell: BackgroundCell?,
    screenRatio: Float,
) {
    val frame by debugViewModel.frameState.collectAsState()

    val collision by debugViewModel.collisionState.collectAsState()

    // fixme 背景が動いてない場合はリロードしない
    Box {
        backgroundCell.fieldData.forEachIndexed { row, backgroundCells ->
            backgroundCells.forEachIndexed { col, cell ->
                if (frame) {
                    CellInfo(
                        cell = cell,
                        row = row,
                        col = col,
                        eventCell = eventCell,
                        screenRatio = screenRatio,
                    )
                }

                if (collision) {
                    CollisionObjects(
                        backgroundCell = cell,
                        screenRatio = screenRatio,
                    )
                }
            }
        }
    }
}

@Composable
fun CellInfo(
    cell: BackgroundCell,
    row: Int,
    col: Int,
    eventCell: BackgroundCell?,
    screenRatio: Float,
) {
    cell.apply {
        Box(
            modifier = Modifier
                .size(
                    width = (rectangle.width * screenRatio).pxToDp(),
                    height = (rectangle.height * screenRatio).pxToDp(),
                )
                .offset(
                    x = (rectangle.leftSide * screenRatio).pxToDp(),
                    y = (rectangle.topSide * screenRatio).pxToDp(),
                )
                .border(
                    width = 1.dp,
                    color = if (this == eventCell) {
                        Colors.PlayerIncludeCell
                    } else {
                        Colors.BackgroundCellBorder
                    },
                    shape = RectangleShape,
                )
        ) {
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

@Composable
fun CollisionObjects(
    backgroundCell: BackgroundCell,
    screenRatio: Float,
) {
    backgroundCell.apply {

        collisionData.forEach {
            Canvas(
                modifier = Modifier
                    .size(
                        width = (rectangle.width * screenRatio).pxToDp(),
                        height = (rectangle.height * screenRatio).pxToDp(),
                    )
                    .offset(
                        x = (x * screenRatio).pxToDp(),
                        y = (y * screenRatio).pxToDp(),
                    ),
                onDraw = {
                    drawPath(
                        path = it.getPath(
                            screenRatio,
                        ),
                        color = Colors.CollisionColor,
                        style = Stroke(width = 10f),
                    )
                }
            )
        }
    }
}
