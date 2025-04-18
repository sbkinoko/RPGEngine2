package gamescreen.map.layout.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import common.extension.pxToDp
import gamescreen.map.domain.Player
import gamescreen.map.domain.background.ObjectData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class MapClip(
    val width: Float,
    val height: Float,
    val player: Player,
    val screenRatio: Float,
) : Shape {
    private val rate = 0.2f
    val path = Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, height)
        lineTo(width, height)
        lineTo(width, 0f)
        lineTo(0f, 0f)
        val horizontalCenter = (player.square.leftSide + player.square.rightSide) / 2f * screenRatio
        val verticalCenter = (player.square.topSide + player.square.bottomSide) / 2f * screenRatio
        val rect = Rect(
            left = horizontalCenter - width * rate / 2,
            right = horizontalCenter + width * rate / 2,
            top = verticalCenter - height * rate / 2,
            bottom = verticalCenter + height * rate / 2,
        )

        arcTo(
            rect = rect,
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true,
        )
        arcTo(
            rect = rect,
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true,
        )

        close()
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(path)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ObjectData(
    objectData: ObjectData,
    screenRatio: Float,
    modifier: Modifier = Modifier,
) {
    val imageBinder = ImageBinderBackground()

    // fixme 背景が動いてない場合はリロードしない
    Box(
        modifier = modifier,

        ) {
        objectData.fieldData.forEach { backgroundCells ->
            backgroundCells.forEach inner@{ cell ->
                if (cell == null) {
                    return@inner
                }

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
                            ),
                    ) {
                        imageBinder.bindObject(
                            aroundCellId = aroundCellId,
                        )?.let {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(
                                    it,
                                ),
                                contentDescription = "mapObject"
                            )
                        }
                    }
                }
            }
        }
    }
}
