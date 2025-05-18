package gamescreen.map.usecase.setplayercenter

import gamescreen.map.domain.Player

interface SetPlayerCenterUseCase {

    /**
     * プレイヤーを中心に移動する
     */
    suspend operator fun invoke(
        player: Player,
    ): Player
}
