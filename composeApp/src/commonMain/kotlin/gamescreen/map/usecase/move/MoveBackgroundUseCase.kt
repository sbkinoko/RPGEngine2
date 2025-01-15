package gamescreen.map.usecase.move

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalSquare

interface MoveBackgroundUseCase {

    /**
     * 背景を移動
     * ↓　fixme repositoryに格納して引数で入力しないようにする
     * 背景表示エリア外に出たら必要に応じてループ移動
     */
    suspend operator fun invoke(
        velocity: Velocity,
        fieldSquare: NormalSquare,
    )
}
