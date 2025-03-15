package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.collision.square.Square
import gamescreen.map.domain.collision.triangle.ConvexPolygon
import values.event.EventType

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
                        EventSquare(
                            x = x,
                            y = y,
                            size = size,
                            eventType = EventType.Water,
                            objectHeight = ObjectHeight.Water,
                        )
                    )
                }

            CellType.Glass -> {
                square.run {
                    listOf(
                        EventSquare(
                            x = x,
                            y = y,
                            size = size,
                            eventType = EventType.Ground,
                            objectHeight = ObjectHeight.Ground,
                        )
                    )
                }
            }

            is CellType.Box ->
                square.run {
                    listOf(
                        EventSquare(
                            x = x + size / 3,
                            y = y + size / 3,
                            size = size / 3,
                            eventType = if (cellType.id.hasItem) {
                                EventType.Box(
                                    id = cellType.id
                                )
                            } else {
                                EventType.None
                            },
                            objectHeight = ObjectHeight.GroundObject,
                        )
                    )
                }

            is CellType.BridgeLeftTop,
            is CellType.BridgeLeftUnder,
            -> {
                square.run {
                    listOf(
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.GroundObject,
                            Point(0f, size),
                            Point(size * 0.1f, size),
                            Point(size, size * 0.5f),
                            Point(size, size * 0.6f),
                        )
                    )
                }
            }

            is CellType.BridgeRightUnder -> {
                square.run {
                    listOf(
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.GroundObject,
                            Point(0f, size * 0.5f),
                            Point(0f, size * 0.6f),
                            Point(size, size),
                            Point(size * 0.9f, size),
                        )
                    )
                }
            }

            is CellType.Bridge -> {
                square.run {
                    listOf(
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.GroundObject,
                            Point(0f, size * 0.5f),
                            Point(0f, size * 0.6f),
                            Point(size, size * 0.5f),
                            Point(size, size * 0.6f),
                        )
                    )
                }
            }
        }
    }
}
