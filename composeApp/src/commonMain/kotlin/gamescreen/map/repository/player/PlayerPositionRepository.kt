package gamescreen.map.repository.player

import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.flow.StateFlow

interface PlayerPositionRepository {
    val playerPositionStateFlow: StateFlow<Square>

    fun getPlayerPosition(): Square

    suspend fun setPlayerPosition(square: Square)

    companion object {
        val initialSquare: Square
            get() = Square(
                x = 0f,
                y = 0f,
                size = 0f,
            )
    }
}
