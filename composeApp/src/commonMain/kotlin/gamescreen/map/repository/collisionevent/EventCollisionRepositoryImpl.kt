package gamescreen.map.repository.collisionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.collision.square.Rectangle
import values.event.BoxId
import values.event.EventType

class EventCollisionRepositoryImpl : EventCollisionRepository {

    override fun collisionData(
        cellType: CellType,
        square: Rectangle,
    ): List<EventSquare> {

        // 当たり判定がないマスなら空を返す
        if (cellType !is CellType.EventObject) {
            return emptyList()
        }

        return when (cellType) {
            CellType.GlassEvent -> square.run {
                listOf(
                    EventSquare(
                        x = x,
                        y = y,
                        size = width,
                        eventType = EventType.Box(BoxId.Box1),
                        objectHeight = ObjectHeight.Water(1),
                    )
                )
            }
        }
    }
}
