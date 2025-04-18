package gamescreen.map.layout.background

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import gamescreen.map.domain.Player

class MapClip(
    val width: Float,
    val height: Float,
    val player: Player,
    val screenRatio: Float,
) : Shape {
    private val rate = 0.2f
    val path = Path().apply {
        // 画面全体を覆う四角形
        moveTo(0f, 0f)
        lineTo(0f, height)
        lineTo(width, height)
        lineTo(width, 0f)
        lineTo(0f, 0f)

        //プレイヤーを中心として全体に対して大きさ比rateの四角形
        val horizontalCenter = (player.square.leftSide + player.square.rightSide) / 2f * screenRatio
        val verticalCenter = (player.square.topSide + player.square.bottomSide) / 2f * screenRatio
        val rect = Rect(
            left = horizontalCenter - width * rate / 2,
            right = horizontalCenter + width * rate / 2,
            top = verticalCenter - height * rate / 2,
            bottom = verticalCenter + height * rate / 2,
        )

        //rectに内接する円を透過
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
