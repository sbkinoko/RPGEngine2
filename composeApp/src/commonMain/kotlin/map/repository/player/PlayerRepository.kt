package map.repository.player

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.Point

interface PlayerRepository {
    val playerPositionFLow: MutableSharedFlow<Point>

    fun getPlayerPosition(): Point

    suspend fun setPlayerPosition(point: Point)

    suspend fun reload()
}
