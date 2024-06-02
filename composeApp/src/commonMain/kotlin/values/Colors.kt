package values

import androidx.compose.ui.graphics.Color

object Colors {
    private val red = Color(255, 0, 0, 255)
    private val gray = Color(100, 100, 100, 100)
    private val blue = Color(0, 0, 255, 255)
    private val white = Color(255, 255, 255, 255)
    private val black = Color(0, 0, 0, 255)

    val Player = red
    val MapBackground = gray
    val BackgroundCell = blue
    val PlayerIncludeCell = red
    val ControllerArea = white
    val ControllerLine = black
}
