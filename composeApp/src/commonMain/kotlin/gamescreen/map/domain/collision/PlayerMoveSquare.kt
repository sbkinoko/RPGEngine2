package gamescreen.map.domain.collision

import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.collision.square.Rectangle

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) {
    val square: Rectangle = NormalRectangle(
        x = screenSize * borderRate,
        y = screenSize * borderRate,
        size = screenSize * (1 - 2 * borderRate),
    )
}
