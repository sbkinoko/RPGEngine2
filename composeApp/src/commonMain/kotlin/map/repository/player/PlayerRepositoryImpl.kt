package map.repository.player

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.collision.Square

class PlayerRepositoryImpl : PlayerRepository {
    override val playerPositionFLow: MutableSharedFlow<Square>
        get() = TODO("Not yet implemented")


    override fun getPlayerPosition(): Square {
        TODO("Not yet implemented")
    }

    override suspend fun setPlayerPosition(square: Square) {
        TODO("Not yet implemented")
    }

    override suspend fun reload() {
        TODO("Not yet implemented")
    }
}
