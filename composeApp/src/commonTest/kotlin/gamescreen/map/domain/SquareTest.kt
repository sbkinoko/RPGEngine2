package gamescreen.map.domain

import gamescreen.map.domain.collision.square.EventSquare
import gamescreen.map.domain.collision.square.NormalSquare
import values.event.EventType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SquareTest {
    // displayPointの値が0の時のテスト
    @Test
    fun move() {
        val dx = 1f
        val dy = 1f

        NormalSquare(
            point = Point(
                x = 0f,
                y = 0f,
            ),
            size = SIZE,
        ).move(
            dx = dx,
            dy = dy,
        ).apply {
            assertEquals(
                expected = 1f,
                actual = leftSide,
            )

            assertEquals(
                expected = 1f,
                actual = topSide,
            )

            assertEquals(
                expected = SIZE,
                actual = width,
            )
        }
    }

    // displayPointの値が0以外の時のテスト
    @Test
    fun moveWithValue() {
        val x = 2f
        val y = 3f
        val dx = 1f
        val dy = 2f
        val size = SIZE * 2

        NormalSquare(
            point = Point(
                x = x,
                y = y,
            ),
            size = size,
        ).move(
            dx = dx,
            dy = dy,
        ).apply {
            assertEquals(
                expected = x + dx,
                actual = leftSide,
            )

            assertEquals(
                expected = y + dy,
                actual = topSide,
            )

            assertEquals(
                expected = size,
                actual = width,
            )
        }
    }

    /**
     * 大きさを確認するテスト
     */
    @Test
    fun side() {
        val square = NormalSquare(
            point = Point(
                x = 10f,
                y = 15f,
            ),
            size = SIZE,
        )

        square.apply {
            assertEquals(
                expected = 10f,
                actual = leftSide
            )

            assertEquals(
                expected = 20f,
                actual = rightSide,
            )

            assertEquals(
                expected = 15f,
                actual = topSide,
            )

            assertEquals(
                expected = 25f,
                actual = bottomSide,
            )
        }
    }

    @Test
    fun isLeft() {
        val square1 = NormalSquare(
            x = 10f,
            y = 0f,
            size = SIZE,
        )
        // 半分かぶってる
        NormalSquare(
            x = 5f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertFalse { this.isLeft(square1) }
        }
        // 辺だけ接してる
        NormalSquare(
            x = 0f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertTrue {
                this.isLeft(square1)
            }
        }
    }

    @Test
    fun isRight() {
        val baseSquare = NormalSquare(
            x = 10f,
            y = 0f,
            size = SIZE,
        )

        // 半分かぶってる
        NormalSquare(
            x = 15f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertFalse { this.isRight(baseSquare) }
        }

        // 辺だけ接してる
        NormalSquare(
            x = 20f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertTrue {
                this.isRight(baseSquare)
            }
        }
    }

    @Test
    fun isUp() {
        val baseSquare = NormalSquare(
            x = 0f,
            y = 10f,
            size = SIZE,
        )

        // 半分かぶってる
        NormalSquare(
            x = 0f,
            y = 5f,
            size = SIZE,
        ).apply {
            assertFalse { this.isUp(baseSquare) }
        }

        // 辺だけ接してる
        NormalSquare(
            x = 0f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertTrue {
                this.isUp(baseSquare)
            }
        }
    }

    @Test
    fun isDown() {
        val baseSquare = NormalSquare(
            x = 0f,
            y = 10f,
            size = SIZE,
        )

        // 半分かぶってる
        NormalSquare(
            x = 0f,
            y = 15f,
            size = SIZE,
        ).apply {
            assertFalse { this.isDown(baseSquare) }
        }

        // 辺だけ接してる
        NormalSquare(
            x = 0f,
            y = 20f,
            size = SIZE,
        ).apply {
            assertTrue {
                this.isDown(baseSquare)
            }
        }
    }

    @Test
    fun isOverlapTop1() {
        val baseSquare = NormalSquare(
            point = Point(10f, 10f),
            size = 10f
        )

        val upSquare1 = NormalSquare(
            point = Point(10f, 0f),
            size = 10f,
        )
        assertTrue { baseSquare.isOverlap(upSquare1) }
        assertTrue { upSquare1.isOverlap(baseSquare) }

        val square2 = upSquare1.move(dx = 0f, dy = -1f)

        assertFalse { baseSquare.isOverlap(square2) }
        assertFalse { square2.isOverlap(baseSquare) }
    }

    /**
     * キリの悪い数値で確認
     */
    @Test
    fun isOverlapTop2() {
        val largeSquare = EventSquare(
            square = NormalSquare(
                point = Point(128.06143f, 133.4598f),
                size = 42f,
                objectHeight = ObjectHeight.Water(0),
            ),
            eventType = EventType.Water,
        )

        val baseSquare = NormalSquare(
            point = Point(135.1815f, 119.99265f),
            size = 20f,
        )

        baseSquare.apply {
            assertTrue { largeSquare.isOverlap(this) }
            assertTrue { this.isOverlap(largeSquare) }
        }
    }

    @Test
    fun isOverlapBottom() {
        val baseSquare = NormalSquare(
            point = Point(10f, 10f),
            size = 10f
        )

        val upSquare1 = NormalSquare(
            point = Point(10f, 20f),
            size = 10f,
        )
        assertTrue { baseSquare.isOverlap(upSquare1) }
        assertTrue { upSquare1.isOverlap(baseSquare) }

        val square2 = upSquare1.move(
            dx = 0f,
            dy = 1f,
        )

        assertFalse { baseSquare.isOverlap(square2) }
        assertFalse { square2.isOverlap(baseSquare) }
    }

    @Test
    fun isOverlapLeft() {
        val baseSquare = NormalSquare(
            point = Point(10f, 10f),
            size = 10f
        )

        val upSquare1 = NormalSquare(
            point = Point(0f, 10f),
            size = 10f,
        )
        assertTrue { baseSquare.isOverlap(upSquare1) }
        assertTrue { upSquare1.isOverlap(baseSquare) }

        val square2 = upSquare1.move(dx = -1f, dy = 0f)

        assertFalse { baseSquare.isOverlap(square2) }
        assertFalse { square2.isOverlap(baseSquare) }
    }

    @Test
    fun isOverlapRight() {
        val baseSquare = NormalSquare(
            point = Point(10f, 10f),
            size = 10f
        )

        val upSquare1 = NormalSquare(
            point = Point(20f, 10f),
            size = 10f,
        )
        assertTrue { baseSquare.isOverlap(upSquare1) }
        assertTrue { upSquare1.isOverlap(baseSquare) }

        val square2 = upSquare1.move(dx = 1f, dy = 0f)

        assertFalse { baseSquare.isOverlap(square2) }
        assertFalse { square2.isOverlap(baseSquare) }
    }


    companion object {
        private const val SIZE = 10f
    }
}
