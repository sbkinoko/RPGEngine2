package core.usecase.updateparameter

import core.domain.status.PlayerStatus

abstract class UpdatePlayerStatusUseCase : AbstractUpdateStatusUseCase<PlayerStatus>() {
    abstract suspend fun deleteToolAt(playerId: Int, index: Int)
}
