package map.repository.player

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.collision.Square

class PlayerRepositoryImpl : PlayerRepository {
    override val playerPositionFLow: MutableSharedFlow<Square> = MutableSharedFlow(replay = 1)

    private lateinit var playerPosition: Square

    override fun getPlayerPosition(): Square {
        return playerPosition
    }

    override suspend fun setPlayerPosition(square: Square) {
        playerPosition = square
        playerPositionFLow.emit(square)
    }

    override suspend fun reload() {
        playerPositionFLow.emit(playerPosition)
    }
}
