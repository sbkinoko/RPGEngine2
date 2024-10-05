package gamescreen.map.layout

import gamescreen.map.domain.collision.Square

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) : Square(
    x = screenSize * borderRate,
    y = screenSize * borderRate,
    size = screenSize * (1 - 2 * borderRate),
)
