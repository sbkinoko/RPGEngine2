package gamescreen.map.repository.backgroundcell

import core.domain.mapcell.CellType
import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.MapData
import kotlinx.coroutines.flow.MutableSharedFlow

interface BackgroundRepository {
    // fixme stateFlowにしたい
    val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>>

    var background: List<List<BackgroundCell>>

    var mapData: MapData

    var cellNum: Int

    val allCellNum: Int

    var screenSize: Int

    val cellSize: Float

    fun getBackgroundAt(x: Int, y: Int): BackgroundCell

    fun getBackgroundAround(x: Int, y: Int): Array<Array<CellType>>

    suspend fun setBackground(background: List<List<BackgroundCell>>)

    suspend fun reload()

    companion object {
        val initialBackground: List<List<BackgroundCell>> = emptyList()
    }
}
