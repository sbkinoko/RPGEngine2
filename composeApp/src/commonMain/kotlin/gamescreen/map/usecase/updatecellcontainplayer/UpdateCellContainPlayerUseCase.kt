package gamescreen.map.usecase.updatecellcontainplayer

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData

/**
 * プレイヤーが入っているセルを更新
 */
interface UpdateCellContainPlayerUseCase {

    operator fun invoke(
        player: Player,
        backgroundData: BackgroundData,
    ): BackgroundCell?

}
