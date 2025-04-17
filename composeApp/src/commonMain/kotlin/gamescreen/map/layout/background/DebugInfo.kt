package gamescreen.map.layout.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import values.Colors
import values.GameParams

@Composable
fun DebugInfo(
    backgroundCell: BackgroundData,
    eventCell: BackgroundCell?,
    screenRatio: Float,
) {
    // fixme 背景が動いてない場合はリロードしない
    Box {
        backgroundCell.fieldData.forEachIndexed { row, backgroundCells ->
            backgroundCells.forEachIndexed { col, cell ->
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

                    CollisionObjects(
                        backgroundCell = this@apply,
                        screenRatio = screenRatio,
                    )
                }
            }
        }
    }
}

@Composable
fun CollisionObjects(
    backgroundCell: BackgroundCell,
    screenRatio: Float,
) {
    if (GameParams.showCollisionObject.value.not()) {
        // 当たり判定の描画が不要ならreturn
        return
    }

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
