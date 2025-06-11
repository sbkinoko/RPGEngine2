package gamescreen.map.repository.backgroundcell

import core.domain.mapcell.CellType
import gamescreen.map.data.MapData
import gamescreen.map.domain.MapPoint

interface BackgroundRepository {

    var mapData: MapData

    var cellNum: Int

    val allCellNum: Int

    var screenSize: Int

    val cellSize: Float

    fun getBackgroundAround(
        x: Int,
        y: Int,
    ): List<List<CellType>>

    fun getBackgroundAround(mapPoint: MapPoint): List<List<CellType>>
}
