package gamescreen.map.layout.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.domain.background.ObjectData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

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
                            cellType = cellType,
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
