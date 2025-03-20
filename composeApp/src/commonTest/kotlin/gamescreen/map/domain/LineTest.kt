package gamescreen.map.domain

import kotlin.test.Test
import kotlin.test.assertEquals

private val origin = Point(
    0f, 0f
)
private val xPoint = Point(
    x = 1f,
    y = 0f,
)
private val yPoint = Point(
    x = 0f,
    y = 1f,
)
private val xyPoint = Point(
    x = 1f,
    y = 1f,
)

val verticalLine = Line(
    origin,
    yPoint,
)

val horizontal = Line(
    origin,
    xPoint,
)

val slope = Line(
    origin,
    xyPoint,
)

class LineTest {
    @Test
    fun isHorizontal() {
        assertEquals(
            expected = Inclination.Slope(0f),
            actual = horizontal.inclination,
        )
    }

    @Test
    fun isVertical() {
        assertEquals(
            expected = Inclination.Vertical,
            actual = verticalLine.inclination
        )
    }

    @Test
    fun isSlope1() {
        assertEquals(
            expected = Inclination.Slope(1f),
            actual = slope.inclination,
        )
    }

    @Test
    fun isSlope2() {
        assertEquals(
            expected = Inclination.Slope(2f),
            actual = Line(
                origin,
                Point(2f, 4f),
            ).inclination,
        )
    }

    @Test
    fun intercept1() {
        assertEquals(
            expected = -0f,
            actual = slope.intercept
        )
    }

    @Test
    fun slope2() {
        val line = Line(
            Point(0f, 1f),
            Point(1f, 3f),
        )
        assertEquals(
            expected = 1f,
            actual = line.intercept,
        )

        assertEquals(
            expected = Inclination.Slope(
                2f,
            ),
            actual = line.inclination
        )
    }

    @Test
    fun slope3() {
        val line = Line(
            Point(0f, 1f),
            Point(1f, -1f),
        )
        assertEquals(
            expected = 1f,
            actual = line.intercept,
        )

        assertEquals(
            expected = Inclination.Slope(
                -2f,
            ),
            actual = line.inclination
        )
    }
}
