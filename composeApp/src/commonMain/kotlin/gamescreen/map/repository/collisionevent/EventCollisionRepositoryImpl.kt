package gamescreen.map.repository.collisionevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.square.EventRectangle
import gamescreen.map.domain.collision.square.Rectangle
import values.event.BoxId
import values.event.EventType

class EventCollisionRepositoryImpl : EventCollisionRepository {

    override fun collisionData(
        cellType: CellType,
        rectangle: Rectangle,
    ): List<EventRectangle> {

        // 当たり判定がないマスなら空を返す
        if (cellType !is CellType.EventObject) {
            return emptyList()
        }

        return when (cellType) {
            CellType.GlassEvent -> rectangle.run {
                listOf(
                    EventRectangle(
                        x = x,
                        y = y,
                        width = width,
                        height = height,
                        eventType = EventType.Box(BoxId.Box1),
                        objectHeight = ObjectHeight.Water(1),
                    )
                )
            }

            CellType.BridgeLeftUnder -> rectangle.run {
                listOf(
                    EventRectangle(
                        x = x,
                        y = y,
                        width = width / 2,
                        height = height,
                        eventType = EventType.Ground1,
                        objectHeight = ObjectHeight.None,
                    ),
                    EventRectangle(
                        x = x + width / 2,
                        y = y,
                        width = width / 2,
                        height = height,
                        eventType = EventType.Ground2,
                        objectHeight = ObjectHeight.None,
                    )
                )
            }

            CellType.BridgeRightUnder -> rectangle.run {
                listOf(
                    EventRectangle(
                        x = x + width / 2,
                        y = y,
                        width = width / 2,
                        height = height,
                        eventType = EventType.Ground1,
                        objectHeight = ObjectHeight.None,
                    ),
                    EventRectangle(
                        x = x,
                        y = y,
                        width = width / 2,
                        height = height,
                        eventType = EventType.Ground2,
                        objectHeight = ObjectHeight.None,
                    )
                )
            }
        }
    }
}
