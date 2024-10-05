package gamescreen.map.usecase

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.player.PlayerRepository
import kotlin.math.abs

class PlayerMoveManageUseCase(
    private val playerRepository: PlayerRepository,
    private val isCollidedUseCase: IsCollidedUseCase,
) {
    private val playerSquare: Square
        get() = playerRepository.getPlayerPosition().getNew()

    /**
     * 移動可能な速度を返す
     */
    fun getMovableVelocity(
        tentativePlayerVelocity: Velocity,
    ): Velocity {
        //　x方向だけの移動ができるかチェック
        val onlyMoveX = playerSquare
        onlyMoveX.move(
            dx = tentativePlayerVelocity.x,
            dy = 0f,
        )
        var canMoveX =
            isCollidedUseCase(onlyMoveX).not()

        //　y方向だけの移動ができるかチェック
        val onlyMoveY = playerSquare
        onlyMoveY.move(
            dx = 0f,
            dy = tentativePlayerVelocity.y,
        )
        var canMoveY =
            isCollidedUseCase(onlyMoveY).not()

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
            canMoveX = canMoveX,
            canMoveY = canMoveY,
        )
    }

    private fun changeVelocity(
        velocity: Velocity,
        canMoveX: Boolean,
        canMoveY: Boolean,
    ): Velocity {
        val vx =
            if (canMoveX) {
                velocity.x
            } else {
                getVx(
                    velocity = velocity,
                )
            }

        val vy =
            if (canMoveY) {
                velocity.y
            } else {
                getVy(
                    velocity = velocity,
                )
            }

        return Velocity(
            x = vx,
            y = vy,
        )
    }

    private fun getVy(
        velocity: Velocity,
    ): Float {
        val dir = velocity.y.toDir()
        var section = Section(
            min = 0f,
            max = abs(velocity.y),
        )

        for (cnt: Int in 0..5) {
            section = getNewSection(
                dx = velocity.x,
                dy = section.average * dir,
                section = section,
            )
        }

        return section.min * dir
    }

    private fun getVx(
        velocity: Velocity,
    ): Float {
        val dir = velocity.x.toDir()
        var section = Section(
            min = 0f,
            max = abs(velocity.x),
        )

        for (cnt: Int in 0..5) {
            section = getNewSection(
                dx = section.average * dir,
                dy = velocity.y,
                section = section,
            )
        }

        return section.min * dir
    }

    private fun getNewSection(
        dx: Float,
        dy: Float,
        section: Section,
    ): Section {
        val square = playerSquare

        square.move(
            dx,
            dy,
        )

        return if (isCollidedUseCase(square)) {
            // 動けないなら最大を更新
            section.copy(
                max = section.average,
            )
        } else {
            // 動けるなら最小を更新
            section.copy(
                min = section.average,
            )
        }
    }

    private fun Float.toDir(): Int {
        return if (0 <= this) {
            1
        } else {
            -1
        }
    }

    private data class Section(
        val min: Float,
        val max: Float,
    ) {
        val average: Float
            get() = (min + max) / 2
    }
}
