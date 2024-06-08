package common

import kotlin.test.Test
import kotlin.test.assertEquals

class NormalizerTest {
    @Test
    fun check0_0() {
        val x = 0f
        val y = 0f
        check(
            x = x,
            y = y,
            max = 5f,
        )
    }

    @Test
    fun check_3_4() {
        check(
            x = 3f,
            y = 4f,
            max = 5,
        )
    }

    @Test
    fun check_m3_4() {
        check(
            x = -3f,
            y = 4f,
            max = 5,
        )
    }

    @Test
    fun check_m3_m4() {
        check(
            x = -3f,
            y = -4f,
            max = 5,
        )
    }

    @Test
    fun check_3_m4() {
        check(
            x = 3f,
            y = -4f,
            max = 5,
        )
    }

    @Test
    fun check_6_8() {
        check(
            x = 6f,
            expectX = 3f,
            y = 8f,
            expectY = 4f,
            max = 5,
        )
    }

    private fun check(
        x: Float,
        expectX: Float = x,
        y: Float,
        expectY: Float = y,
        max: Number,
    ) {
        Normalizer.normalize(
            x = x,
            y = y,
            max = max,
        ).apply {
            assertEquals(
                expected = expectX,
                actual = this.x,
            )
            assertEquals(
                expected = expectY,
                actual = this.y,
            )
        }
    }
}
