package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class MapDataTest {

    private val mapData = MapData()

    @Test
    fun testGetXY() {
        mapData.getDataAt(
            x = 0,
            y = 0,
        ).apply {
            assertEquals(
                1,
                this,
            )
        }

        mapData.getDataAt(
            x = 0,
            y = 9,
        ).apply {
            assertEquals(
                2,
                this,
            )
        }

        mapData.getDataAt(
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
        mapData.getDataAt(
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

        mapData.getDataAt(
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

        mapData.getDataAt(
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
