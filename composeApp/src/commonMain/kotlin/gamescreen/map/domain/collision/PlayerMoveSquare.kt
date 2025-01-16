package gamescreen.map.domain.collision

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Square

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) {
    val square: Square = NormalSquare(
        x = screenSize * borderRate,
        y = screenSize * borderRate,
        size = screenSize * (1 - 2 * borderRate),
    )
}
