package gamescreen.map.repository.player

import gamescreen.map.domain.collision.square.Square
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerPositionRepositoryImpl : PlayerPositionRepository {
    private val mutablePlayerPositionStateFlow = MutableStateFlow(
        PlayerPositionRepository.initialSquare
    )

    override val playerPositionStateFlow: StateFlow<Square>
        get() = mutablePlayerPositionStateFlow.asStateFlow()

    override fun getPlayerPosition(): Square {
        return playerPositionStateFlow.value
    }

    override suspend fun setPlayerPosition(square: Square) {
        mutablePlayerPositionStateFlow.value = square
    }

}
