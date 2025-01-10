package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.EventSquare
import gamescreen.map.domain.collision.Square
import values.EventType

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
        cellType: CellType,
        square: Square,
    ): List<CollisionDetectShape> {

        // 当たり判定がないマスなら空を返す
        if (cellType !is CellType.CollisionCell) {
            return emptyList()
        }

        return when (cellType) {
            CellType.Water ->
                square.run {
                    listOf(
                        Square(
                            x = x,
                            y = y,
                            size = size
                        )
                    )
                }

            is CellType.Box ->
                square.run {
                    listOf(
                        EventSquare(
                            x = x + size / 3,
                            y = y + size / 3,
                            size = size / 3,
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
}
