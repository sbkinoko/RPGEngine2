package map.manager

import map.domain.Player
import map.domain.Velocity

class MoveManager {

    /**
     * 移動可能な速度を返す
     */
    fun getMovableVelocity(
        player: Player,
        tentativePlayerVelocity: Velocity,
        backgroundManger: BackgroundManager,
    ): Velocity {
        //　x方向だけの移動ができるかチェック
        val onlyMoveX = player.square.getNew()
        onlyMoveX.move(
            dx = tentativePlayerVelocity.x,
            dy = 0f,
        )
        var canMoveX =
            backgroundManger.isCollided(onlyMoveX).not()

        //　y方向だけの移動ができるかチェック
        val onlyMoveY = player.square.getNew()
        onlyMoveY.move(
            dx = 0f,
            dy = tentativePlayerVelocity.y,
        )
        var canMoveY =
            backgroundManger.isCollided(onlyMoveY).not()

        // 両方に移動できる場合は速い方に動かす
        if (canMoveX && canMoveY) {
            if (tentativePlayerVelocity.y <= tentativePlayerVelocity.x) {
//                canMoveX = true
                canMoveY = false
            } else {
                canMoveX = false
//                canMoveY = true
            }
        }

        return changeVelocity(
            velocity = tentativePlayerVelocity,
            player = player,
            backgroundManger = backgroundManger,
            canMoveX = canMoveX,
            canMoveY = canMoveY,
        )
    }

    private fun changeVelocity(
        velocity: Velocity,
        player: Player,
        backgroundManger: BackgroundManager,
        canMoveX: Boolean,
        canMoveY: Boolean,
    ): Velocity {
        if (canMoveX) {
            var vMin =
                if (0 <= velocity.y) {
                    0f
                } else {
                    velocity.y
                }
            var vMax = if (0 <= velocity.y) {
                velocity.y
            } else {
                0f
            }

            var vy = (vMin + vMax) / 2

            for (cnt: Int in 0..5) {
                val square = player.square.getNew()
                square.move(
                    velocity.x,
                    vy,
                )
                if (backgroundManger.isCollided(square)) {
                    if (0 <= velocity.y) {
                        vMax = vy
                    } else {
                        vMin = vy
                    }
                } else {
                    if (0 <= velocity.y) {
                        vMin = vy
                    } else {
                        vMax = vy
                    }
                }
                vy = (vMin + vMax) / 2
            }
            return if (0 <= velocity.y) {
                velocity.copy(
                    y = vMin,
                )
            } else {
                velocity.copy(
                    y = vMax,
                )
            }
        }

        return Velocity(
            x = 0f,
            y = 0f,
        )
    }
}
