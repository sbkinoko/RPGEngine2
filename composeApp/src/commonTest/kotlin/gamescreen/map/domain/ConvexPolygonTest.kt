package gamescreen.map.domain

import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.collision.triangle.ConvexPolygon
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// todo 四角形以上の多角形のテストを作る
class ConvexPolygonTest {
    private val rightTriangle = ConvexPolygon(
        pointList = listOf(
            Point(10f, 10f),
            Point(10f, 20f),
            Point(20f, 20f),
        ),
    )

    @Test
    fun checkRightTriangleCollide1() {
        val square = NormalRectangle(
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
        val square = NormalRectangle(
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
        val square = NormalRectangle(
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
        val square = NormalRectangle(
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
}
