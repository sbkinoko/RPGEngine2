package core.domain.status.param

import core.domain.status.IncData
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusParameterTest {

    /**
     * 初期化のテスト
     */
    @Test
    fun init() {
        val value = 10
        val status = StatusParameter<ParameterType.SPD>(value)

        assertEquals(
            expected = value,
            actual = status.value
        )
    }

    /**
     * 加算処理のテスト
     */
    @Test
    fun inc() {
        val value = 10
        val status = StatusParameter<ParameterType.SPD>(value)

        val inc = 5
        val incStatus = status.inc(IncData(inc))

        assertEquals(
            expected = value + inc,
            actual = incStatus.value
        )
    }

    /**
     * 正の場合のテスト
     */
    @Test
    fun bufTestAdd() {
        val value = 10
        val buf = 5
        val status = StatusParameter<ParameterType.SPD>(value)
        val updated = status.grantBuf(
            Buf(
                buf,
                1,
                BufType.Add,
            ),
        )

        assertEquals(
            expected = value + buf,
            actual = updated.value,
        )

        val buf2 = 6
        val updated2 = updated.grantBuf(
            Buf(
                buf2,
                1,
                BufType.Add,
            )
        )

        assertEquals(
            expected = value + buf + buf2,
            actual = updated2.value,
        )
    }

    /**
     * 負の場合のテスト
     */
    @Test
    fun bufTestAdd2() {
        val value = 10
        val buf = -5
        val status = StatusParameter<ParameterType.SPD>(value)
        val updated = status.grantBuf(
            Buf(
                buf,
                1,
                BufType.Add,
            ),
        )

        assertEquals(
            expected = value + buf,
            actual = updated.value,
        )

        val buf2 = -6
        val updated2 = updated.grantBuf(
            Buf(
                buf2,
                1,
                BufType.Add,
            )
        )

        assertEquals(
            expected = 0,
            actual = updated2.value,
        )
    }

    /**
     * ターン経過でバフが減ることの確認
     */
    @Test
    fun reduceTest() {
        val value = 10
        val buf = 5

        val status = StatusParameter<ParameterType.SPD>(value)
        var updated = status.grantBuf(
            Buf(
                buf,
                3,
                BufType.Add,
            ),
        ).grantBuf(
            Buf(
                buf,
                4,
                BufType.Add,
            ),
        )

        updated = updated.reduceBuf().apply {
            assertEquals(
                expected = 2,
                actual = addBuf.size,
            )
        }

        updated = updated.reduceBuf().apply {
            assertEquals(
                expected = 2,
                actual = addBuf.size,
            )
        }

        updated = updated.reduceBuf().apply {
            assertEquals(
                expected = 1,
                actual = addBuf.size,
            )
        }

        updated.reduceBuf().apply {
            assertEquals(
                expected = 0,
                actual = addBuf.size,
            )
        }
    }
}
