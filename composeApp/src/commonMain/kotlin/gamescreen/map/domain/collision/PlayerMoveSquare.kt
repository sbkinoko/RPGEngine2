package gamescreen.map.domain.collision

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Rectangle

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) {
    val square: Rectangle = NormalSquare(
        x = screenSize * borderRate,
        y = screenSize * borderRate,
        size = screenSize * (1 - 2 * borderRate),
    )
}
