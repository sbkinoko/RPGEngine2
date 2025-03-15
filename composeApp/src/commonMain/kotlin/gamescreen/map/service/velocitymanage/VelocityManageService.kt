package gamescreen.map.service.velocitymanage

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.Rectangle

interface VelocityManageService {

    /**
     * 移動可能領域とプレイヤーの位置を比較して、どっちをどう動かすかを調整するメソッド
     */
    operator fun invoke(
        tentativePlayerVelocity: Velocity,
        playerMoveArea: Rectangle,
        playerSquare: Rectangle,
    ): Pair<Velocity, Velocity>
}
