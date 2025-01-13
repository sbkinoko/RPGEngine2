package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.NormalSquare

interface CollisionRepository {
    fun collisionData(
        cellType: CellType,
        square: NormalSquare,
    ): List<ShapeCollisionDetect>
}
