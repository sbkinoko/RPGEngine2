package map.domain

import gamescreen.map.domain.Velocity
import kotlin.test.Test
import kotlin.test.assertEquals

class VelocityTest {
    @Test
    fun max5x3y4() {
        val velocity = Velocity(
            x = 3f,
            y = 4f,
            maxVelocity = 5f,
        )
        assertEquals(
            expected = 3f,
            actual = velocity.x,
        )
        assertEquals(
            expected = 4f,
            actual = velocity.y
        )
    }

    @Test
    fun max5x6y8() {
        val velocity = Velocity(
            x = 6f,
            y = 8f,
            maxVelocity = 5f,
        )
        assertEquals(
            expected = 3f,
            actual = velocity.x,
        )
        assertEquals(
            expected = 4f,
            actual = velocity.y
        )
    }

    @Test
    fun max10x3y4() {
        val velocity = Velocity(
            x = 3f,
            y = 4f,
            maxVelocity = 5f,
        )
        assertEquals(
            expected = 3f,
            actual = velocity.x,
        )
        assertEquals(
            expected = 4f,
            actual = velocity.y
        )
    }
}
