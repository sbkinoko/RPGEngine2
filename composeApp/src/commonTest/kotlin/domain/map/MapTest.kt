package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class MapTest {

    private val map = Map()

    @Test
    fun testGetXY() {
        map.getDataAt(
            x = 0,
            y = 0,
        ).apply {
            assertEquals(
                1,
                this,
            )
        }

        map.getDataAt(
            x = 0,
            y = 9,
        ).apply {
            assertEquals(
                2,
                this,
            )
        }

        map.getDataAt(
            x = 9,
            y = 9,
        ).apply {
            assertEquals(
                1,
                this,
            )
        }
    }

    @Test
    fun testGetMapPoint() {
        map.getDataAt(
            MapPoint(
                x = 0,
                y = 0,
            )
        ).apply {
            assertEquals(
                1,
                this,
            )
        }

        map.getDataAt(
            MapPoint(
                x = 0,
                y = 9,
            )
        ).apply {
            assertEquals(
                2,
                this,
            )
        }

        map.getDataAt(
            MapPoint(
                x = 9,
                y = 9,
            )
        ).apply {
            assertEquals(
                1,
                this,
            )
        }
    }
}
