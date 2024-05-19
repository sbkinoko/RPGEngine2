package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class SquareTest {
    // displayPointの値が0の時のテスト
    @Test
    fun move() {
        val square = Square(
            displayPoint = Point(
                x = 0f,
                y = 0f,
            ),
            size = 10f,
        )
        val dx = 1f
        val dy = 1f
        square.move(
            dx = dx,
            dy = dy,
        )

        assertEquals(
            expected = 1f,
            actual = square.leftSide,
        )

        assertEquals(
            expected = 1f,
            actual = square.topSide,
        )
    }

    // displayPointの値が0以外の時のテスト
    @Test
    fun moveWithValue() {
        val square = Square(
            displayPoint = Point(
                x = 2f,
                y = 2f,
            ),
            size = 10f,
        )

        val dx = 1f
        val dy = 1f
        square.move(
            dx = dx,
            dy = dy,
        )

        square.apply {
            assertEquals(
                expected = 3f,
                actual = leftSide,
            )

            assertEquals(
                expected = 3f,
                actual = topSide,
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
            size = 10f,
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
}
