package gamescreen.map.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LineCrossTest {

    /**
     *　自身とは必ず交差する
     */
    @Test
    fun isCrossWithSelf() {
        assertTrue {
            slope.isCrossWith(slope)
        }

        assertTrue {
            verticalLine.isCrossWith(verticalLine)
        }

        assertTrue {
            horizontal.isCrossWith(horizontal)
        }
    }

    /**
     * 原点で十字で交差
     */
    @Test
    fun isCross1() {
        val line1 = Line(
            Point(-1f, 0f),
            Point(1f, 0f),
        )

        val line2 = Line(
            Point(0f, -1f),
            Point(0f, 1f),
        )

        assertTrue {
            line1.isCrossWith(line2)
        }

        assertTrue {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 原点でXで交差
     */
    @Test
    fun isCross2() {
        val line1 = Line(
            Point(-1f, -1f),
            Point(1f, 1f),
        )

        val line2 = Line(
            Point(-1f, 1f),
            Point(1f, -1f),
        )

        assertTrue {
            line1.isCrossWith(line2)
        }

        assertTrue {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 端点で交差　同じ傾き
     */
    @Test
    fun isCrossEnd1() {
        val line1 = Line(
            Point(0f, 0f),
            Point(1f, 1f),
        )

        val line2 = Line(
            Point(1f, 1f),
            Point(2f, 2f),
        )

        assertTrue {
            line1.isCrossWith(line2)
        }

        assertTrue {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 端点で交差　異なる傾き
     */
    @Test
    fun isCrossEnd2() {
        val line1 = Line(
            Point(0f, 0f),
            Point(1f, 1f),
        )

        val line2 = Line(
            Point(1f, 1f),
            Point(2f, 3f),
        )

        assertTrue {
            line1.isCrossWith(line2)
        }

        assertTrue {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 水平と垂直で交差しない
     */
    @Test
    fun isNotCross1() {
        val line1 = Line(
            Point(-1f, 0f),
            Point(0f, 0f),
        )

        val line2 = Line(
            Point(1f, 1f),
            Point(1f, -1f),
        )

        assertFalse {
            line1.isCrossWith(line2)
        }

        assertFalse {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 斜め同士で交差しない
     */
    @Test
    fun isNotCross2() {
        val line1 = Line(
            Point(0f, 0f),
            Point(1f, 1f),
        )

        val line2 = Line(
            Point(0f, 3f),
            Point(3f, 0f),
        )

        assertFalse {
            line1.isCrossWith(line2)
        }

        assertFalse {
            line2.isCrossWith(line1)
        }
    }

    /**
     * 斜め同士で交差しない
     */
    @Test
    fun isNotCross3() {
        val line1 = Line(
            Point(0f, 0f),
            Point(1f, 2f),
        )

        val line2 = Line(
            Point(0f, 4f),
            Point(2f, 3f),
        )

        assertFalse {
            line1.isCrossWith(line2)
        }

        assertFalse {
            line2.isCrossWith(line1)
        }
    }
}
