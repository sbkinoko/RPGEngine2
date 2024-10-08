package controller.domain

import androidx.compose.ui.geometry.Offset
import kotlin.test.Test
import kotlin.test.assertEquals

class StickPositionTest {
    @Test
    fun moveStickNoTap() {
        Stick(
            areaRadius = AREA_RADIUS,
            stickRadius = STICK_RADIUS,
        ).apply {
            assertEquals(
                expected = 0,
                actual = x,
            )

            assertEquals(
                expected = 0,
                actual = y,
            )
        }
    }

    @Test
    fun moveStickInAreaTap() {
        val dx = 3f
        val dy = 4f
        Stick(
            areaRadius = AREA_RADIUS,
            stickRadius = STICK_RADIUS,
            position = Offset(
                x = dx + AREA_RADIUS,
                y = dy + AREA_RADIUS,
            )
        ).apply {
            assertEquals(
                expected = dx.toInt(),
                actual = x,
            )

            assertEquals(
                expected = dy.toInt(),
                actual = y,
            )
        }
    }

    @Test
    fun moveStickOutAreaTap() {
        val dx = 6f
        val dy = 8f
        val areaRatio = (AREA_RADIUS - STICK_RADIUS) / AREA_RADIUS.toFloat()
        Stick(
            areaRadius = AREA_RADIUS,
            stickRadius = STICK_RADIUS,
            position = Offset(
                x = dx + AREA_RADIUS,
                y = dy + AREA_RADIUS,
            )
        ).apply {
            assertEquals(
                expected = (dx * areaRatio).toInt(),
                actual = x,
            )

            assertEquals(
                expected = (dy * areaRatio).toInt(),
                actual = y,
            )
        }
    }

    companion object {
        private const val AREA_RADIUS = 10
        private const val STICK_RADIUS = 2
    }
}
