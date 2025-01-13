package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Square
import values.EventType

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
        cellType: CellType,
        square: Square,
    ): List<ShapeCollisionDetect> {

        // 当たり判定がないマスなら空を返す
        if (cellType !is CellType.CollisionCell) {
            return emptyList()
        }

        return when (cellType) {
            CellType.Water ->
                square.run {
                    listOf(
                        NormalSquare(
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
