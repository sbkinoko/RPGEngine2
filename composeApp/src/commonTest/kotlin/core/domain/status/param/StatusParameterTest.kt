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
}
