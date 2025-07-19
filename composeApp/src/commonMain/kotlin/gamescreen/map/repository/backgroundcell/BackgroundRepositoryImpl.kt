package gamescreen.map.repository.backgroundcell

import core.domain.mapcell.CellType
import gamescreen.map.data.LoopMap
import gamescreen.map.data.MapData
import gamescreen.map.domain.MapPoint
import gamescreen.map.viewmodel.MapViewModel

class BackgroundRepositoryImpl : BackgroundRepository {
    override var mapData: MapData = LoopMap()

    override var cellNum: Int = 3

    override val allCellNum: Int
        get() = cellNum + 1

    override var screenSize: Int = MapViewModel.VIRTUAL_SCREEN_SIZE
    override val cellSize: Float
        get() = screenSize / cellNum.toFloat()

    override fun getBackgroundAround(mapPoint: MapPoint): List<List<CellType>> {
        return getBackgroundAround(
            mapPoint.x,
            mapPoint.y,
        )
    }

    override fun getBackgroundAround(
        x: Int,
        y: Int,
    ): List<List<CellType>> {
        return listOf(
            listOf(
                getIdAt(x = x - 1, y = y - 1),
                getIdAt(x = x, y = y - 1),
                getIdAt(x = x + 1, y = y - 1),
            ),
            listOf(
                getIdAt(x = x - 1, y = y),
                getIdAt(x = x, y = y),
                getIdAt(x = x + 1, y = y),
            ),
            listOf(
                getIdAt(x = x - 1, y = y + 1),
                getIdAt(x = x, y = y + 1),
                getIdAt(x = x + 1, y = y + 1),
            ),
        )
    }

    private fun getIdAt(
        x: Int,
        y: Int,
    ): CellType {
        return if (mapData.isLoop) {
            mapData.getDataAt(
                x = correctX(x),
                y = correctY(y)
            )
        } else {
            if (isOutX(x) || isOutY(y)) {
                mapData.outerCell
            } else {
                mapData.getDataAt(x, y)
            }
        }
    }

    private fun isOutX(x: Int): Boolean {
        return (x < 0 || x >= mapData.width)
    }

    private fun isOutY(y: Int): Boolean {
        return (y < 0 || y >= mapData.height)
    }

    private fun correctX(x: Int): Int {
        if (x < 0) {
            return mapData.width - 1
        }

        if (x >= mapData.width) {
            return 0
        }

        return x
    }

    private fun correctY(y: Int): Int {
        if (y < 0) {
            return mapData.height - 1
        }

        if (y >= mapData.height) {
            return 0
        }

        return y
    }
}
