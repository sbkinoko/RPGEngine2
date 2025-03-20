package gamescreen.map.repository.collisionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.square.EventRectangle
import gamescreen.map.domain.collision.square.Rectangle

interface EventCollisionRepository {
    fun collisionData(
        cellType: CellType,
        rectangle: Rectangle,
    ): List<EventRectangle>
}
