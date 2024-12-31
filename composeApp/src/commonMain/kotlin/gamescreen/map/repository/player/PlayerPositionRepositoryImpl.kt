package gamescreen.map.repository.player

import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerPositionRepositoryImpl : PlayerPositionRepository {
    override val playerPositionFLow: MutableSharedFlow<Square> = MutableSharedFlow(replay = 1)

    private var playerPosition: Square = PlayerPositionRepository.initialSquare

    private val mutablePlayerPositionStateFlow = MutableStateFlow(
        PlayerPositionRepository.initialSquare
    )

    override val playerPositionStateFlow: StateFlow<Square>
        get() = mutablePlayerPositionStateFlow.asStateFlow()

    override fun getPlayerPosition(): Square {
        return playerPosition
    }

    override suspend fun setPlayerPosition(square: Square) {
        playerPosition = square
        playerPositionFLow.emit(square)
        mutablePlayerPositionStateFlow.value = square
    }

    override suspend fun reload() {
        playerPositionFLow.emit(playerPosition)
    }
}
