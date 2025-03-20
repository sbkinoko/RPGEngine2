package gamescreen.map.usecase.movebackground

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle

interface MoveBackgroundUseCase {

    /**
     * 背景を移動
     * 背景表示エリア外に出たら必要に応じてループ移動
     */
    operator fun invoke(
        velocity: Velocity,
        backgroundData: BackgroundData,
        fieldSquare: NormalRectangle,
    ): BackgroundData
}
