package gamescreen.map.layout.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import common.extension.pxToDp
import gamescreen.map.domain.background.ObjectData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import values.Colors

class MapClip(
    val width: Float,
    val height: Float,
) : Shape {
    val path = Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, height)
        lineTo(width, height)
        lineTo(width, 0f)
        lineTo(0f, 0f)
        moveTo(width, height / 2)
        arcTo(
            rect = Rect(
                left = 0f,
                right = width,
                top = 0f,
                bottom = height,
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false,
        )
        arcTo(
            rect = Rect(
                left = 0f,
                right = width,
                top = 0f,
                bottom = height,
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false,
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
) {
    val imageBinder = ImageBinderBackground()

    var width by remember {
        mutableStateOf(0)
    }
    var height by remember {
        mutableStateOf(0)
    }

    // fixme 背景が動いてない場合はリロードしない
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                width = it.size.width
                height = it.size.height
            }
            .clip(
                shape = MapClip(
                    width = width.toFloat(),
                    height = height.toFloat(),
                ),
            )
            .background(
                color = Colors.Disabled,
            )
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
