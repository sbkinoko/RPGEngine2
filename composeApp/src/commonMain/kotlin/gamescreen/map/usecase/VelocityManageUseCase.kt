package gamescreen.map.usecase

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.Square
import gamescreen.map.repository.player.PlayerPositionRepository

class VelocityManageUseCase(
    private val playerPositionRepository: PlayerPositionRepository,
) {
    /**
     * 移動可能領域とプレイヤーの位置を比較して、どっちをどう動かすかを調整するメソッド
     */
    fun manageVelocity(
        tentativePlayerVelocity: Velocity,
        playerMoveArea: Square,
    ): Pair<Velocity, Velocity> {
        // 背景の移動速度
        val vbx: Float
        val vby: Float

        // プレイヤーの移動速度
        val vpx: Float
        val vpy: Float
        val player = playerPositionRepository.getPlayerPosition()

        if ((player.isLeft(playerMoveArea) &&
                    tentativePlayerVelocity.x < 0) ||
            (player.isRight(playerMoveArea) &&
                    0 < tentativePlayerVelocity.x)
        ) {
            vbx = -(tentativePlayerVelocity.x)
            vpx = 0f
        } else {
            vpx = tentativePlayerVelocity.x
            vbx = 0f
        }

        if ((player.isUp(playerMoveArea) &&
                    tentativePlayerVelocity.y < 0) ||
            (player.isDown(playerMoveArea) &&
                    0 < tentativePlayerVelocity.y)
        ) {
            vby = -(tentativePlayerVelocity.y)
            vpy = 0f
        } else {
            vby = 0f
            vpy = tentativePlayerVelocity.y
        }

        val backGroundVelocity = Velocity(
            x = vbx,
            y = vby,
        )
        val playerVelocity = Velocity(
            x = vpx,
            y = vpy,
        )

        return Pair(playerVelocity, backGroundVelocity)
    }
}
