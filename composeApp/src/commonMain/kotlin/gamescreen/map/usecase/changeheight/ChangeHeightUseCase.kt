package gamescreen.map.usecase.changeheight

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player

interface ChangeHeightUseCase {
    suspend operator fun invoke(
        targetHeight: ObjectHeight,
        player: Player,
    ): Player
}
