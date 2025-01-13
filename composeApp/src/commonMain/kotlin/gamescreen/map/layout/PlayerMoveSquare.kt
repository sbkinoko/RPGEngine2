package gamescreen.map.layout

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Square
import gamescreen.map.domain.collision.square.SquareWrapper

data class PlayerMoveSquare(
    val screenSize: Int,
    val borderRate: Float,
    override val square: Square = NormalSquare(
        x = screenSize * borderRate,
        y = screenSize * borderRate,
        size = screenSize * (1 - 2 * borderRate),
    ),
) : SquareWrapper<PlayerMoveSquare>(
    square = square,
) {
    override fun move(dx: Float, dy: Float): PlayerMoveSquare {
        return this.copy(
            square = square.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(x: Float, y: Float): PlayerMoveSquare {
        return this.copy(
            square = square.moveTo(
                x = x,
                y = y,
            )
        )
    }
}
