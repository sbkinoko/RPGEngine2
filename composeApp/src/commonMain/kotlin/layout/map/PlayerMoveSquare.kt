package layout.map

import domain.map.Square

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) : Square(
    x = screenSize * borderRate,
    y = screenSize * borderRate,
    size = screenSize * (1 - 2 * borderRate),
)
