package common

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class FPSCounterTest {

    val counter = FpsCounter()

    @Test
    fun test() {

        counter.addInfo(0)
        repeat(60) {
            counter.addInfo(600L)
        }

        assertEquals(
            100,
            counter.fpsFlow.value,
        )
    }

    @Test
    fun test1() {

        var t = 0L
        counter.addInfo(t)
        repeat(60) {
            t += 33
            counter.addInfo(t)
        }

        assertEquals(
            30,
            counter.fpsFlow.value,
        )

        t += 33
        counter.addInfo(t)

        assertEquals(
            30,
            counter.fpsFlow.value,
        )
    }

    @Test
    fun test2() {
        var t = 0L
        counter.addInfo(t)
        repeat(60) {
            t += Random.nextInt() % 100 + 1
            counter.addInfo(t)
        }

        assertEquals(
            60 * 1000 / t,
            counter.fpsFlow.value,
        )
    }
}
