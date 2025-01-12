package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.Square

interface CollisionRepository {
    fun collisionData(
        cellType: CellType,
        square: Square,
    ): List<CollisionDetectShape>
}
