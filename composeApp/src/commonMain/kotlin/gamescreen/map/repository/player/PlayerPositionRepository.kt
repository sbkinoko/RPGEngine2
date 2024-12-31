package gamescreen.map.repository.player

import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface PlayerPositionRepository {
    // fixme stateFlowにしたい
    val playerPositionFLow: MutableSharedFlow<Square>
    val playerPositionStateFlow: StateFlow<Square>

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
