package map.layout

import map.domain.Square

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) : Square(
    x = screenSize * borderRate,
    y = screenSize * borderRate,
    size = screenSize * (1 - 2 * borderRate),
)
