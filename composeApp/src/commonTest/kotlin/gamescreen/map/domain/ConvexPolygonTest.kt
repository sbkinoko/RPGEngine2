package gamescreen.map.domain

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.triangle.ConvexPolygon
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// todo 四角形以上の多角形のテストを作る
class ConvexPolygonTest {
    private val rightTriangle = ConvexPolygon(
        baseX = 0f,
        baseY = 0f,
        pointList = listOf(
            Point(10f, 10f),
            Point(10f, 20f),
            Point(20f, 20f),
        ),
    )

    @Test
    fun checkRightTriangleCollide1() {
        val square = NormalSquare(
            size = 2f,
            x = 9f,
            y = 14f,
        )

        assertTrue {
            rightTriangle.isOverlap(square)
        }

        assertTrue {
            square.isOverlap(rightTriangle)
        }
    }

    @Test
    fun checkRightTriangleCollide2() {
        val square = NormalSquare(
            size = 2f,
            x = 14f,
            y = 19f,
        )

        assertTrue {
            rightTriangle.isOverlap(square)
        }

        assertTrue {
            square.isOverlap(rightTriangle)
        }
    }

    @Test
    fun checkRightTriangleCollide3() {
        val square = NormalSquare(
            size = 2f,
            x = 14f,
            y = 14f,
        )

        assertTrue {
            rightTriangle.isOverlap(square)
        }

        assertTrue {
            square.isOverlap(rightTriangle)
        }
    }

    @Test
    fun checkRightTriangleNotCollide() {
        val square = NormalSquare(
            size = 2f,
            x = 12f,
            y = 15f,
        )

        assertFalse {
            rightTriangle.isOverlap(square)
        }

        assertFalse {
            square.isOverlap(rightTriangle)
        }
    }


    private val triangle = ConvexPolygon(
        baseX = 10f,
        baseY = 10f,
        pointList = listOf(
            Point(5f, 0f),
            Point(0f, 10f),
            Point(9.9f, 9.9f),
        ),
    )

    @Test
    fun checkTriangleNotCollide1() {
        val square = NormalSquare(
            size = 2f,
            x = 8.9f,
            y = 16f,
        )
        assertFalse {
            square.isOverlap(triangle)
        }

        assertFalse {
            triangle.isOverlap(square)
        }
    }

    @Test
    fun checkTriangleCollide1() {
        val square = NormalSquare(
            size = 2f,
            x = 9f,
            y = 16f,
        )
        assertTrue {
            square.isOverlap(triangle)
        }

        assertTrue {
            triangle.isOverlap(square)
        }
    }

    @Test
    fun checkTriangleCollide2() {
        val square = NormalSquare(
            size = 2f,
            x = 12f,
            y = 16f,
        )
        assertTrue {
            square.isOverlap(triangle)
        }

        assertTrue {
            triangle.isOverlap(square)
        }
    }

    @Test
    fun checkTriangleNotCollide2() {
        val square = NormalSquare(
            size = 2f,
            x = 12.1f,
            y = 16f,
        )
        assertFalse {
            square.isOverlap(triangle)
        }

        assertFalse {
            triangle.isOverlap(square)
        }
    }

    @Test
    fun checkTriangleCollide3() {
        val square = NormalSquare(
            size = 2f,
            x = 18f,
            y = 16f,
        )
        assertTrue {
            square.isOverlap(triangle)
        }

        assertTrue {
            triangle.isOverlap(square)
        }
    }

    @Test
    fun checkTriangleCollide4() {
        val square = NormalSquare(
            size = 2f,
            x = 14f,
            y = 19f,
        )
        assertTrue {
            square.isOverlap(triangle)
        }

        assertTrue {
            triangle.isOverlap(square)
        }
    }
}
