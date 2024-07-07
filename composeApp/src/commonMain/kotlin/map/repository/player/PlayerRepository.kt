package map.repository.player

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.collision.Square

interface PlayerRepository {
    val playerPositionFLow: MutableSharedFlow<Square>

    fun getPlayerPosition(): Square

    suspend fun setPlayerPosition(square: Square)

    suspend fun reload()
}
