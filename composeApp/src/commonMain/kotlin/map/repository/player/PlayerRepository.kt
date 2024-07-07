package map.repository.player

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.collision.Square

interface PlayerRepository {
    val playerPositionFLow: MutableSharedFlow<Square>

    fun getPlayerPosition(): Square

    suspend fun setPlayerPosition(square: Square)

    suspend fun reload()

    companion object {
        val initialSquare: Square
            get() = Square(
                x = 0f,
                y = 0f,
                size = 0f,
            )
    }
}
