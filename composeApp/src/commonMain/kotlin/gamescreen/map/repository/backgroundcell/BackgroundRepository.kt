package gamescreen.map.repository.backgroundcell

import core.domain.mapcell.CellType
import gamescreen.map.data.MapData
import gamescreen.map.domain.BackgroundCell
import kotlinx.coroutines.flow.StateFlow

interface BackgroundRepository {
    val backgroundStateFlow: StateFlow<List<List<BackgroundCell>>>

    var mapData: MapData

    var cellNum: Int

    val allCellNum: Int

    var screenSize: Int

    val cellSize: Float

    fun getBackgroundAt(x: Int, y: Int): BackgroundCell

    fun getBackgroundAround(x: Int, y: Int): Array<Array<CellType>>

    suspend fun setBackground(background: List<List<BackgroundCell>>)

    companion object {
        val initialBackground: List<List<BackgroundCell>> = emptyList()
    }
}
