package map.repository.backgroundcell

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.BackgroundCell
import map.domain.MapData

interface BackgroundRepository {
    val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>>

    var background: List<List<BackgroundCell>>

    var mapData: MapData

    fun getBackgroundAt(x: Int, y: Int): BackgroundCell

    suspend fun setBackground(background: List<List<BackgroundCell>>)

    suspend fun reload()

    companion object {
        val initialBackground: List<List<BackgroundCell>> = emptyList()
    }
}
