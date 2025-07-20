package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import core.domain.mapcell.FenceDir
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.ObjectHeightDetail
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
                            x = 0f,
                            y = 0f,
                            width = width,
                            height = height,
                            eventType = EventType.Water,
                            objectHeight = ObjectHeight.Water(ObjectHeightDetail.Back),
                        )
                    )
                }

            CellType.Glass,
                -> {
                rectangle.run {
                    listOf(
                        EventRectangle(
                            x = 0f,
                            y = 0f,
                            width = width,
                            height = height,
                            eventType = EventType.Ground,
                            objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Back),
                        )
                    )
                }
            }

            is CellType.Box ->
                rectangle.run {
                    listOf(
                        EventRectangle(
                            x = width / 3,
                            y = height / 3,
                            width = width / 3,
                            height = height / 3,
                            eventType = if (cellType.hasItem) {
                                EventType.Box(
                                    id = cellType.id
                                )
                            } else {
                                EventType.None
                            },
                            objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                        )
                    )
                }

            is CellType.BridgeLeftTop ->
                rectangle.run {
                    listOf(
                        ConvexPolygon(
                            objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                            Point(0f, height),
                            Point(width * 0.1f, height),
                            Point(width, height * 0.5f),
                            Point(width, height * 0.6f),
                        ),
                        ConvexPolygon(
                            objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Front),
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
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                        Point(0f, height),
                        Point(width, height * 0.5f),
                        Point(width, height),
                    ),
                    ConvexPolygon(
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Front),
                        Point(0f, height),
                        Point(width, height * 0.5f),
                        Point(width, height),
                    ),
                    // 右端にふた
                    NormalRectangle(
                        x = width * 0.9f,
                        y = 0f,
                        width = 0.1f * width,
                        height = height,
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal)
                    )
                )
            }

            is CellType.BridgeRightTop -> rectangle.run {
                listOf(
                    ConvexPolygon(
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                        Point(0f, height * 0.5f),
                        Point(0f, height * 0.6f),
                        Point(width, height),
                        Point(width * 0.9f, height),
                    ),
                    ConvexPolygon(
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Front),
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
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                        Point(0f, height * 0.5f),
                        Point(0f, height),
                        Point(width, height),
                    ),
                    ConvexPolygon(
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Front),
                        Point(0f, height * 0.5f),
                        Point(0f, height),
                        Point(width, height),
                    ),
                    // 左端にふた
                    NormalRectangle(
                        x = 0f,
                        y = 0f,
                        width = 0.1f * width,
                        height = height,
                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal)
                    )
                )
            }

            is CellType.BridgeCenterTop,
            is CellType.BridgeCenterBottom,
                -> {
                rectangle.run {
                    listOf(
                        ConvexPolygon(
                            objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Front),
                            Point(0f, height * 0.5f),
                            Point(0f, height * 0.6f),
                            Point(width, height * 0.5f),
                            Point(width, height * 0.6f),
                        )
                    )
                }
            }

            is CellType.Fence -> {
                when (cellType.dir) {
                    FenceDir.LR -> rectangle.run {
                        listOf(
                            ConvexPolygon(
                                objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                Point(0f, height * 0.6f),
                                Point(0f, height * 0.4f),
                                Point(width, height * 0.4f),
                                Point(width, height * 0.6f),
                            )
                        )
                    }

                    FenceDir.UD -> {
                        rectangle.run {
                            listOf(
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(width * 0.4f, 0f),
                                    Point(width * 0.4f, height),
                                    Point(width * 0.6f, height),
                                    Point(width * 0.6f, 0f),
                                )
                            )
                        }
                    }

                    FenceDir.LU -> {
                        rectangle.run {
                            listOf(
                                // 縦
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(width * 0.4f, 0f),
                                    Point(width * 0.4f, height * 0.6f),
                                    Point(width * 0.6f, height * 0.6f),
                                    Point(width * 0.6f, 0f),
                                ),
                                // 横
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(0f, height * 0.6f),
                                    Point(0f, height * 0.4f),
                                    Point(width * 0.6f, height * 0.4f),
                                    Point(width * 0.6f, height * 0.6f),
                                ),
                            )
                        }
                    }

                    FenceDir.RU -> {
                        rectangle.run {
                            listOf(
                                // 縦
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(width * 0.4f, 0f),
                                    Point(width * 0.4f, height * 0.6f),
                                    Point(width * 0.6f, height * 0.6f),
                                    Point(width * 0.6f, 0f),
                                ),
                                // 横
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(width * 0.4f, height * 0.6f),
                                    Point(width * 0.6f, height * 0.4f),
                                    Point(width * 1f, height * 0.4f),
                                    Point(width * 1f, height * 0.6f),
                                ),
                            )
                        }
                    }

                    FenceDir.LD -> {
                        rectangle.run {
                            listOf(
                                // 縦
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(width * 0.4f, height * 0.4f),
                                    Point(width * 0.4f, height * 1f),
                                    Point(width * 0.6f, height * 1f),
                                    Point(width * 0.6f, height * 0.4f),
                                ),
                                // 横
                                ConvexPolygon(
                                    objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                    Point(0f, height * 0.6f),
                                    Point(0f, height * 0.4f),
                                    Point(width * 0.6f, height * 0.4f),
                                    Point(width * 0.6f, height * 0.6f),
                                ),
                            )
                        }
                    }

                    FenceDir.RD -> {
                        rectangle.run {
                            rectangle.run {
                                listOf(
                                    // 縦
                                    ConvexPolygon(
                                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                        Point(width * 0.4f, height * 0.4f),
                                        Point(width * 0.4f, height * 1f),
                                        Point(width * 0.6f, height * 1f),
                                        Point(width * 0.6f, height * 0.4f),
                                    ),
                                    // 横
                                    ConvexPolygon(
                                        objectHeight = ObjectHeight.Ground(ObjectHeightDetail.Normal),
                                        Point(width * 0.4f, height * 0.6f),
                                        Point(width * 0.4f, height * 0.4f),
                                        Point(width * 1f, height * 0.4f),
                                        Point(width * 1f, height * 0.6f),
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
