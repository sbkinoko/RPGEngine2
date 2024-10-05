package gamescreen.map.domain

import gamescreen.map.data.LoopTestMap
import kotlin.test.Test
import kotlin.test.assertEquals

class MapDataTest {

    private val mapData = LoopTestMap()

    @Test
    fun testGetXY() {
        mapData.getDataAt(
            x = 0,
            y = 0,
        ).apply {
            assertEquals(
                0,
                this,
            )
        }

        mapData.getDataAt(
            x = 0,
            y = 3,
        ).apply {
            assertEquals(
                12,
                this,
            )
        }

        mapData.getDataAt(
            x = 3,
            y = 3,
        ).apply {
            assertEquals(
                15,
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
                0,
                this,
            )
        }

        mapData.getDataAt(
            MapPoint(
                x = 0,
                y = 3,
            )
        ).apply {
            assertEquals(
                12,
                this,
            )
        }

        mapData.getDataAt(
            MapPoint(
                x = 3,
                y = 3,
            )
        ).apply {
            assertEquals(
                15,
                this,
            )
        }
    }
}
