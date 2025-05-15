package core.usecase.restart

import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData

interface RestartUseCase {
    suspend operator fun invoke(
        player: Player,
    ): UIData
}
