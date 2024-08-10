package common.values

import androidx.compose.ui.graphics.Color

object Colors {
    private val red = Color(255, 0, 0, 255)
    private val blue = Color(0, 0, 255, 255)
    private val white = Color(255, 255, 255, 255)
    private val black = Color(0, 0, 0, 255)
    private val transGreen = Color(0, 255, 0, 200)

    val Player = red
    val BackgroundCell = blue
    val PlayerIncludeCell = red
    val ControllerArea = white
    val ControllerLine = black
    val MenuAra = transGreen

    val BattleCommand = blue
    val MonsterArea = red
    val StatusArea = black
    val StatusComponent = blue

    val IsActivePlayer = black
    val NotActivePlayer = red

    val SelectedMenu = red
    val MenuFrame = blue
}
