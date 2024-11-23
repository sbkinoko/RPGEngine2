package gamescreen.map.repository.collision

import core.domain.MapConst.Companion.BOX____
import core.domain.MapConst.Companion.WATER__
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.EventSquare
import gamescreen.map.domain.collision.Square
import values.EventConstants

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
        id: Int,
        cellSize: Float,
        square: Square,
    ): List<CollisionDetectShape> {
        return when (id) {
            WATER__ ->
                listOf(
                    Square(
                        x = square.x,
                        y = square.y,
                        size = cellSize
                    )
                )

            BOX____ ->
                listOf(
                    EventSquare(
                        x = square.x + cellSize / 3,
                        y = square.y + cellSize / 3,
                        size = cellSize / 3,
                        eventID = EventConstants.Box
                    )
                )

            else -> emptyList()
        }
    }
}
