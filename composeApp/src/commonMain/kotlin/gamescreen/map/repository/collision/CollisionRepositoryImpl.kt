package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.collision.square.EventRectangle
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.domain.collision.triangle.ConvexPolygon
import values.event.EventType

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
        cellType: CellType,
        rectangle: Rectangle,
    ): List<ShapeCollisionDetect> {

        // 当たり判定がないマスなら空を返す
        if (cellType !is CellType.CollisionCell) {
            return emptyList()
        }

        // fixme 水平方向に反転する処理を作りたい
        return when (cellType) {
            CellType.Water ->
                rectangle.run {
                    listOf(
                        EventRectangle(
                            x = x,
                            y = y,
                            width = width,
                            height = height,
                            eventType = EventType.Water,
                            objectHeight = ObjectHeight.Water(0),
                        )
                    )
                }

            CellType.Glass -> {
                rectangle.run {
                    listOf(
                        EventRectangle(
                            x = x,
                            y = y,
                            width = width,
                            height = height,
                            eventType = EventType.Ground,
                            objectHeight = ObjectHeight.Ground(0),
                        )
                    )
                }
            }

            is CellType.Box ->
                rectangle.run {
                    listOf(
                        EventRectangle(
                            x = x + width / 3,
                            y = y + height / 3,
                            width = width / 3,
                            height = height / 3,
                            eventType = if (cellType.id.hasItem) {
                                EventType.Box(
                                    id = cellType.id
                                )
                            } else {
                                EventType.None
                            },
                            objectHeight = ObjectHeight.Ground(1),
                        )
                    )
                }

            is CellType.BridgeLeftTop ->
                rectangle.run {
                    listOf(
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.Ground(1),
                            Point(0f, height),
                            Point(width * 0.1f, height),
                            Point(width, height * 0.5f),
                            Point(width, height * 0.6f),
                        ),
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.Ground(2),
                            Point(0f, height),
                            Point(width * 0.1f, height),
                            Point(width, height * 0.5f),
                            Point(width, height * 0.6f),
                        ),
                    )
                }

            is CellType.BridgeLeftUnder -> rectangle.run {
                listOf(
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(1),
                        Point(0f, height),
                        Point(width, height * 0.5f),
                        Point(width, height),
                    ),
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(2),
                        Point(0f, height),
                        Point(width, height * 0.5f),
                        Point(width, height),
                    ),
                    // 右端にふた
                    NormalRectangle(
                        x = x + width * 0.9f,
                        y = y,
                        width = 0.1f * width,
                        height = height,
                        objectHeight = ObjectHeight.Ground(1)
                    )
                )
            }

            is CellType.BridgeRightTop -> rectangle.run {
                listOf(
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(1),
                        Point(0f, height * 0.5f),
                        Point(0f, height * 0.6f),
                        Point(width, height),
                        Point(width * 0.9f, height),
                    ),
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(2),
                        Point(0f, height * 0.5f),
                        Point(0f, height * 0.6f),
                        Point(width, height),
                        Point(width * 0.9f, height),
                    ),
                )
            }

            is CellType.BridgeRightUnder -> rectangle.run {
                listOf(
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(1),
                        Point(0f, height * 0.5f),
                        Point(0f, height),
                        Point(width, height),
                    ),
                    ConvexPolygon(
                        baseX = x,
                        baseY = y,
                        objectHeight = ObjectHeight.Ground(2),
                        Point(0f, height * 0.5f),
                        Point(0f, height),
                        Point(width, height),
                    ),
                    // 左端にふた
                    NormalRectangle(
                        x = x,
                        y = y,
                        width = 0.1f * width,
                        height = height,
                        objectHeight = ObjectHeight.Ground(1)
                    )
                )
            }

            is CellType.BridgeCenterTop,
            is CellType.BridgeCenterBottom,
                -> {
                rectangle.run {
                    listOf(
                        ConvexPolygon(
                            baseX = x,
                            baseY = y,
                            objectHeight = ObjectHeight.Ground(2),
                            Point(0f, height * 0.5f),
                            Point(0f, height * 0.6f),
                            Point(width, height * 0.5f),
                            Point(width, height * 0.6f),
                        )
                    )
                }
            }
        }
    }
}
