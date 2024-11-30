package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.EventSquare
import gamescreen.map.domain.collision.Square
import values.EventType

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
        cellType: CellType,
        cellSize: Float,
        square: Square,
    ): List<CollisionDetectShape> {
        if (cellType !is CellType.CollisionCell) {
            return emptyList()
        }

        return when (cellType) {
            CellType.Water ->
                listOf(
                    Square(
                        x = square.x,
                        y = square.y,
                        size = cellSize
                    )
                )

            is CellType.Box ->
                listOf(
                    EventSquare(
                        x = square.x + cellSize / 3,
                        y = square.y + cellSize / 3,
                        size = cellSize / 3,
                        eventID = if (cellType.id.hasItem) {
                            EventType.Box(
                                id = cellType.id
                            )
                        } else {
                            EventType.None
                        }
                    )
                )
        }
    }
}
