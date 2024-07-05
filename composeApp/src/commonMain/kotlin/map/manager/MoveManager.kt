package map.manager

import map.domain.Player
import map.domain.Velocity
import kotlin.math.abs

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
        val vy =
            if (canMoveX) {
                getVy(
                    velocity = velocity,
                    player = player,
                    backgroundManger = backgroundManger,
                )
            } else {
                velocity.y
            }

        val vx =
            if (canMoveY) {
                getVx(
                    velocity = velocity,
                    player = player,
                    backgroundManger = backgroundManger,
                )
            } else {
                velocity.x
            }

        return Velocity(
            x = vx,
            y = vy,
        )
    }

    private fun getVy(
        velocity: Velocity,
        player: Player,
        backgroundManger: BackgroundManager,
    ): Float {
        val dir = if (0 <= velocity.y) {
            1
        } else {
            -1
        }

        var vMin = 0f
        var vMax = abs(velocity.y)

        var vy = (vMin + vMax) / 2

        for (cnt: Int in 0..5) {
            val square = player.square.getNew()
            square.move(
                velocity.x,
                vy * dir,
            )
            if (backgroundManger.isCollided(square)) {
                vMax = vy
            } else {
                vMin = vy
            }
            vy = (vMin + vMax) / 2
        }

        return vMin * dir
    }

    private fun getVx(
        velocity: Velocity,
        player: Player,
        backgroundManger: BackgroundManager,
    ): Float {
        val dir = if (0 <= velocity.x) {
            1
        } else {
            -1
        }

        var vMin = 0f
        var vMax = abs(velocity.x)

        var vx = (vMin + vMax) / 2

        for (cnt: Int in 0..5) {
            val square = player.square.getNew()
            square.move(
                vx * dir,
                velocity.y,
            )
            if (backgroundManger.isCollided(square)) {
                vMax = vx
            } else {
                vMin = vx
            }
            vx = (vMin + vMax) / 2
        }

        return vMin * dir
    }
}
