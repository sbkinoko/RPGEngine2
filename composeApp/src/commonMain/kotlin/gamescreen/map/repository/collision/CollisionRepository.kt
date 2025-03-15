package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.Rectangle

interface CollisionRepository {
    fun collisionData(
        cellType: CellType,
        square: Rectangle,
    ): List<ShapeCollisionDetect>
}
