package common.status.param

import core.domain.status.param.StatusPointData
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusDataPointTest {
    companion object {
        private const val MAX = 10
        private const val POINT = 5
    }

    val data = StatusPointData(
        maxPoint = MAX,
        point = POINT,
    )

    /**
     * 初期値を1つだけ入れた場合のテスト
     */
    @Test
    fun checkInit1Param() {
        val val1 = 10
        val data = StatusPointData(
            maxPoint = val1,
        )

        assertEquals(
            expected = val1,
            actual = data.maxPoint,
        )

        assertEquals(
            expected = val1,
            actual = data.point,
        )
    }

    /**
     * 初期値を2つ入れた場合のテスト
     */
    @Test
    fun checkInit2Param() {
        val val1 = 10
        val val2 = 11
        val data = StatusPointData(
            maxPoint = val1,
            point = val2,
        )

        assertEquals(
            expected = val1,
            actual = data.maxPoint,
        )

        assertEquals(
            expected = val2,
            actual = data.point,
        )
    }

    /**
     * 最大値を更新した場合のテスト
     */
    @Test
    fun incMax() {
        val dif = 5
        val updated = data.incMax(dif)

        assertEquals(
            expected = MAX + dif,
            actual = updated.maxPoint
        )

        assertEquals(
            expected = POINT,
            actual = updated.point,
        )
    }

    /**
     * 最大値を上回らないように加算した場合のテスト
     */
    @Test
    fun inc1() {
        val dif = MAX - POINT - 1
        val updated = data.inc(dif)

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint
        )

        assertEquals(
            expected = POINT + dif,
            actual = updated.point,
        )
    }

    /**
     * 最大値を上回るように加算した場合のテスト
     */
    @Test
    fun inc2() {
        val dif = MAX - POINT + 1
        val updated = data.inc(dif)

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint
        )

        assertEquals(
            expected = MAX,
            actual = updated.point,
        )
    }

    /**
     * 0を下回らない様に減算した場合のテスト
     */
    @Test
    fun dec1() {
        val dif = POINT - 1
        val updated = data.dec(dif)

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint
        )

        assertEquals(
            expected = POINT - dif,
            actual = updated.point,
        )
    }

    /**
     * 0を下回る様に減算した場合のテスト
     */
    @Test
    fun dec2() {
        val dif = POINT + 1
        val updated = data.dec(dif)

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint
        )

        assertEquals(
            expected = 0,
            actual = updated.point,
        )
    }

    /**
     * 最大値より小さい値にした場合のテスト
     */
    @Test
    fun setValue1() {
        val amount = 7
        val updated = data.set(
            value = amount,
        )

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint,
        )

        assertEquals(
            expected = amount,
            actual = updated.point
        )
    }

    /**
     * 最大値より大きい値にした場合のテスト
     */
    @Test
    fun setValue2() {
        val amount = MAX + 1
        val updated = data.set(
            value = amount,
        )

        assertEquals(
            expected = MAX,
            actual = updated.maxPoint,
        )

        assertEquals(
            expected = MAX,
            actual = updated.point
        )
    }

    /**
     * 最大値を現在の値より大きい値にした場合のテスト
     */
    @Test
    fun setMaxValue1() {
        val amount = 7
        val updated = data.set(
            maxValue = amount,
        )

        assertEquals(
            expected = amount,
            actual = updated.maxPoint,
        )

        assertEquals(
            expected = POINT,
            actual = updated.point
        )
    }

    /**
     * 最大値を現在の値より小さい値にした場合のテスト
     */
    @Test
    fun setMaxValue2() {
        val amount = 4
        val updated = data.set(
            maxValue = amount,
        )

        assertEquals(
            expected = amount,
            actual = updated.maxPoint,
        )

        assertEquals(
            expected = amount,
            actual = updated.point
        )
    }
}
