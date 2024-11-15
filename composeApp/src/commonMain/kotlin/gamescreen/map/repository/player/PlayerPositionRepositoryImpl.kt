package gamescreen.map.repository.player

import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.flow.MutableSharedFlow

class PlayerPositionRepositoryImpl : PlayerPositionRepository {
    override val playerPositionFLow: MutableSharedFlow<Square> = MutableSharedFlow(replay = 1)

    private var playerPosition: Square = PlayerPositionRepository.initialSquare

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
