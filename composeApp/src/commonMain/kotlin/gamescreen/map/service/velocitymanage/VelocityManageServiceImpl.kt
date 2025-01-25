package gamescreen.map.service.velocitymanage

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.Square

class VelocityManageServiceImpl : VelocityManageService {

    override fun invoke(
        tentativePlayerVelocity: Velocity,
        playerMoveArea: Square,
        playerSquare: Square,
    ): Pair<Velocity, Velocity> {
        // 背景の移動速度
        val vbx: Float
        val vby: Float

        // プレイヤーの移動速度
        val vpx: Float
        val vpy: Float

        if ((playerSquare.isLeft(playerMoveArea) &&
                    tentativePlayerVelocity.x < 0) ||
            (playerSquare.isRight(playerMoveArea) &&
                    0 < tentativePlayerVelocity.x)
        ) {
            vbx = -(tentativePlayerVelocity.x)
            vpx = 0f
        } else {
            vpx = tentativePlayerVelocity.x
            vbx = 0f
        }

        if ((playerSquare.isUp(playerMoveArea) &&
                    tentativePlayerVelocity.y < 0) ||
            (playerSquare.isDown(playerMoveArea) &&
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
