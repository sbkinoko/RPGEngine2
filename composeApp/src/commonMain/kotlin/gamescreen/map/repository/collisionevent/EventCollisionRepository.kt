package gamescreen.map.repository.collisionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.collision.square.Rectangle

interface EventCollisionRepository {
    fun collisionData(
        cellType: CellType,
        square: Rectangle,
    ): List<EventSquare>
}
