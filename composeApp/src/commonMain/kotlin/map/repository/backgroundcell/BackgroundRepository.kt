package map.repository.backgroundcell

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.BackgroundCell

interface BackgroundRepository {
    val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>>

    val background: List<List<BackgroundCell>>

    fun getBackgroundAt(x: Int, y: Int): BackgroundCell

    suspend fun setBackground(background: List<List<BackgroundCell>>)

    suspend fun reload()

    companion object {
        val initialBackground: List<List<BackgroundCell>> = emptyList()
    }
}
