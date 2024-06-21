package map.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import common.values.Colors
import map.manager.BackgroundManager
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun showBackground(
    backgroundManager: BackgroundManager,
    screenRatio: Float,
) {
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
