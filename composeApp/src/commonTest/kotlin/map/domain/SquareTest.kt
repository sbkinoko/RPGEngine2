package map.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SquareTest {
    // displayPointの値が0の時のテスト
    @Test
    fun move() {
        Square(
            displayPoint = Point(
                x = 0f,
                y = 0f,
            ),
            size = SIZE,
        ).apply {
            val dx = 1f
            val dy = 1f
            move(
                dx = dx,
                dy = dy,
            )

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
                actual = size,
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

        Square(
            displayPoint = Point(
                x = x,
                y = y,
            ),
            size = size,
        ).apply {
            move(
                dx = dx,
                dy = dy,
            )

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
                actual = this.size,
            )
        }
    }

    /**
     * 大きさを確認するテスト
     */
    @Test
    fun side() {
        val square = Square(
            displayPoint = Point(
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
        val square1 = Square(
            x = 10f,
            y = 0f,
            size = SIZE,
        )
        // 半分かぶってる
        Square(
            x = 5f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertFalse { this.isLeft(square1) }
        }
        // 辺だけ接してる
        Square(
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
        val baseSquare = Square(
            x = 10f,
            y = 0f,
            size = SIZE,
        )

        // 半分かぶってる
        Square(
            x = 15f,
            y = 0f,
            size = SIZE,
        ).apply {
            assertFalse { this.isRight(baseSquare) }
        }

        // 辺だけ接してる
        Square(
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
        val baseSquare = Square(
            x = 0f,
            y = 10f,
            size = SIZE,
        )

        // 半分かぶってる
        Square(
            x = 0f,
            y = 5f,
            size = SIZE,
        ).apply {
            assertFalse { this.isUp(baseSquare) }
        }

        // 辺だけ接してる
        Square(
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
        val baseSquare = Square(
            x = 0f,
            y = 10f,
            size = SIZE,
        )

        // 半分かぶってる
        Square(
            x = 0f,
            y = 15f,
            size = SIZE,
        ).apply {
            assertFalse { this.isDown(baseSquare) }
        }

        // 辺だけ接してる
        Square(
            x = 0f,
            y = 20f,
            size = SIZE,
        ).apply {
            assertTrue {
                this.isDown(baseSquare)
            }
        }
    }

    companion object {
        private const val SIZE = 10f
    }
}
