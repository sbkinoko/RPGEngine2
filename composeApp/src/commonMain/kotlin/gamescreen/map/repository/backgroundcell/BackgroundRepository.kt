package gamescreen.map.repository.backgroundcell

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.MapData
import kotlinx.coroutines.flow.MutableSharedFlow

interface BackgroundRepository {
    val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>>

    var background: List<List<BackgroundCell>>

    var mapData: MapData

    var cellNum: Int

    val allCellNum: Int

    var screenSize: Int

    val cellSize: Float

    fun getBackgroundAt(x: Int, y: Int): BackgroundCell

    fun getBackgroundAround(x: Int, y: Int): Array<Array<Int>>

    suspend fun setBackground(background: List<List<BackgroundCell>>)

    suspend fun reload()

    companion object {
        val initialBackground: List<List<BackgroundCell>> = emptyList()
    }
}
