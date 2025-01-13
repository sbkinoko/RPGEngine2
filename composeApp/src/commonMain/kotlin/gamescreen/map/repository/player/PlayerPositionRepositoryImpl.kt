package gamescreen.map.repository.player

import gamescreen.map.domain.collision.square.NormalSquare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerPositionRepositoryImpl : PlayerPositionRepository {
    private val mutablePlayerPositionStateFlow = MutableStateFlow(
        PlayerPositionRepository.initialSquare
    )

    override val playerPositionStateFlow: StateFlow<NormalSquare>
        get() = mutablePlayerPositionStateFlow.asStateFlow()

    override fun getPlayerPosition(): NormalSquare {
        return playerPositionStateFlow.value
    }

    override suspend fun setPlayerPosition(square: NormalSquare) {
        mutablePlayerPositionStateFlow.value = square
    }

}
