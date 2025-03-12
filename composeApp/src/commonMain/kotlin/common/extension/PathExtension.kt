package common.extension

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.Point

fun Path.moveTo(
    point: Point,
    screenRatio: Float,
) {
    this.moveTo(
        point.x * screenRatio,
        point.y * screenRatio,
    )
}

fun Path.lineTo(
    point: Point,
    screenRatio: Float,
) {
    this.lineTo(
        point.x * screenRatio,
        point.y * screenRatio,
    )
}
