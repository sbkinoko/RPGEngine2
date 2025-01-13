package gamescreen.map.repository.player

import gamescreen.map.domain.collision.square.NormalSquare
import kotlinx.coroutines.flow.StateFlow

interface PlayerPositionRepository {
    val playerPositionStateFlow: StateFlow<NormalSquare>

    fun getPlayerPosition(): NormalSquare

    suspend fun setPlayerPosition(square: NormalSquare)

    companion object {
        val initialSquare: NormalSquare
            get() = NormalSquare(
                x = 0f,
                y = 0f,
                size = 0f,
            )
    }
}
