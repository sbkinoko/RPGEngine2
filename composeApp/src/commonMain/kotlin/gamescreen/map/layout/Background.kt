package gamescreen.map.layout

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.pxToDp
import gamescreen.map.domain.BackgroundCell
import gamescreen.map.viewmodel.MapViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import values.Colors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Background(
    mapViewModel: MapViewModel,
    screenRatio: Float,
) {
    val backgroundCell: List<List<BackgroundCell>> by mapViewModel
        .backgroundCells
        .collectAsState()

    val eventCell by mapViewModel
        .playerIncludeCellFlow
        .collectAsState()

    val npc by mapViewModel
        .npcFlow
        .collectAsState()

    val imageBinder = ImageBinder()

    // fixme 背景が動いてない場合はリロードしない
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
                                (square.size * screenRatio).pxToDp()
                            )
                            .offset(
                                x = (square.leftSide * screenRatio).pxToDp(),
                                y = (square.topSide * screenRatio).pxToDp(),
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
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(
                                imageBinder.bindBackGround(
                                    aroundCellId = aroundCellId,
                                )
                            ),
                            contentDescription = "background"
                        )

                        imageBinder.bindObject(
                            aroundCellId = aroundCellId,
                        )?.let {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(
                                    it,
                                ),
                                contentDescription = "background"
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

                    val collisionList = mapViewModel
                        .getCollisionList(
                            backgroundCell = this,
                        )
                    collisionList.forEach {

                        Canvas(
                            modifier = Modifier
                                .size(
                                    (square.size * screenRatio).pxToDp()
                                )
                                .offset(
                                    x = (it.baseX * screenRatio).pxToDp(),
                                    y = (it.baseY * screenRatio).pxToDp(),
                                ),
                            onDraw = {
                                drawPath(
                                    path = it.getPath(
                                        screenRatio,
                                    ),
                                    color = Colors.CollisionColor,
                                )
                            }
                        )
                    }

                    npc.forEach {
                        Image(
                            modifier = Modifier
                                .size(
                                    (it.size * screenRatio).pxToDp()
                                )
                                .offset(
                                    x = (it.baseX * screenRatio).pxToDp(),
                                    y = (it.baseY * screenRatio).pxToDp(),
                                ),
                            painter = painterResource(
                                imageBinder.bindNPC()
                            ),
                            contentDescription = "NPC"
                        )
                    }
                }
            }
        }
    }
}
