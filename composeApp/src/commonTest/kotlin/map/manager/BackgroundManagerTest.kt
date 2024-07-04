package map.manager

import map.data.LoopTestMap
import map.domain.collision.Square
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BackgroundManagerTest {

    private lateinit var backgroundManager: BackgroundManager

    @BeforeTest
    fun beforeTest() {
        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
            mapData = LoopTestMap(),
        )
    }

    @Test
    fun countCell() {
        backgroundManager.getCell(
            col = 0,
            row = 0,
        ).apply {
            assertEquals(
                expected = 0,
                this.imgID,
            )
        }
        backgroundManager.getCell(
            col = 2,
            row = 2,
        ).apply {
            assertEquals(
                expected = 10,
                this.imgID,
            )
        }
    }

    @Test
    fun checkInitPosition() {
        val cell1 = backgroundManager.getCell(0, 0)

        assertEquals(
            expected = 10f,
            actual = cell1.cellSize,
        )

        cell1.square.apply {
            assertEquals(
                expected = 0f,
                actual = leftSide,
            )
            assertEquals(
                expected = 0f,
                actual = topSide,
            )
        }

        val cell2 = backgroundManager.getCell(1, 0)
        cell2.square.apply {
            assertEquals(
                expected = 10f,
                actual = leftSide,
            )
            assertEquals(
                expected = 0f,
                actual = topSide,
            )
        }

        val cell3 = backgroundManager.getCell(0, 1)
        cell3.square.apply {
            assertEquals(
                expected = 0f,
                actual = leftSide,
            )
            assertEquals(
                expected = 10f,
                actual = topSide,
            )
        }
    }

    @Test
    fun move() {
        backgroundManager.moveBackgroundCell(dx = 10f, dy = 5f)
        backgroundManager.getCell(0, 0).square.apply {
            assertEquals(
                expected = 10f,
                actual = leftSide,
            )
            assertEquals(
                expected = 5f,
                actual = topSide,
            )
        }
    }

    @Test
    fun getCenterDisplay() {
        backgroundManager.getCenterOfDisplay().apply {
            assertEquals(
                expected = SIDE_LENGTH / 2f,
                actual = x,
            )
            assertEquals(
                expected = SIDE_LENGTH / 2f,
                actual = y,
            )
        }
    }

    @Test
    fun playerIncludeSquare() {
        val square = Square(
            x = 1f,
            y = 1f,
            size = 5f,
        )

        backgroundManager.apply {
            // 最初に全身が入ってるからnullじゃない
            findCellIncludePlayer(square)
            val cell1 = eventCell
            assertTrue {
                cell1 != null
            }

            // 前回のマスから動いてないからnull
            findCellIncludePlayer(square)
            val cell2 = eventCell
            assertTrue {
                cell2 == null
            }

            // 全身が入ってないから動いたけどnull
            square.moveTo(
                x = 7.5f,
                y = 7.5f,
            )
            findCellIncludePlayer(square)
            val cell3 = eventCell
            assertTrue {
                cell3 == null
            }

            // 全身が入ったからnullじゃない
            square.moveTo(
                x = 0.5f,
                y = 0.5f,
            )
            findCellIncludePlayer(square)
            val cell4 = eventCell
            assertTrue {
                cell4 != null
            }
        }
    }

    @Test
    fun resetPosition() {
        backgroundManager.apply {
            getCell(0, 0).apply {
                square.apply {
                    assertEquals(
                        0f,
                        x,
                    )
                    assertEquals(
                        0f,
                        y,
                    )
                }
                mapPoint.apply {
                    assertEquals(
                        3,
                        x,
                    )
                    assertEquals(
                        3,
                        y,
                    )
                }
            }
            resetBackgroundCellPosition(
                mapX = 1,
                mapY = 1
            )
            getCell(0, 0).apply {
                square.apply {
                    assertEquals(
                        0f,
                        x,
                    )
                    assertEquals(
                        0f,
                        y,
                    )
                }
                mapPoint.apply {
                    assertEquals(
                        0,
                        x,
                    )
                    assertEquals(
                        0,
                        y,
                    )
                }
            }
        }
    }
}
