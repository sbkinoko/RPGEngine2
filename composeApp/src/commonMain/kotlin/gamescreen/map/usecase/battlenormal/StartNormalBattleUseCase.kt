package gamescreen.map.usecase.battlenormal

import gamescreen.map.domain.Player
import gamescreen.map.domain.UIData

interface StartNormalBattleUseCase {
    operator fun invoke(
        player: Player,
        updateScreen: (UIData) -> Unit,
    )
}
