package gamescreen.map.layout

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.SquareWrapper

class PlayerMoveSquare(
    screenSize: Int,
    borderRate: Float,
) : SquareWrapper(
    NormalSquare(
        x = screenSize * borderRate,
        y = screenSize * borderRate,
        size = screenSize * (1 - 2 * borderRate),
    )
)
