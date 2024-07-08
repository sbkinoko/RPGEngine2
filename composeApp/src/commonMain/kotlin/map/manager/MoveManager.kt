package map.manager

import map.domain.Velocity
import map.domain.collision.Square
import map.repository.player.PlayerRepository
import kotlin.math.abs

class MoveManager(
    private val playerRepository: PlayerRepository,
) {
    private val playerSquare: Square
        get() = playerRepository.getPlayerPosition().getNew()

    /**
     * 移動可能な速度を返す
     */
    fun getMovableVelocity(
        tentativePlayerVelocity: Velocity,
        backgroundManger: BackgroundManager,
    ): Velocity {
        //　x方向だけの移動ができるかチェック
        val onlyMoveX = playerSquare
        onlyMoveX.move(
            dx = tentativePlayerVelocity.x,
            dy = 0f,
        )
        var canMoveX =
            backgroundManger.isCollided(onlyMoveX).not()

        //　y方向だけの移動ができるかチェック
        val onlyMoveY = playerSquare
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
            backgroundManger = backgroundManger,
            canMoveX = canMoveX,
            canMoveY = canMoveY,
        )
    }

    private fun changeVelocity(
        velocity: Velocity,
        backgroundManger: BackgroundManager,
        canMoveX: Boolean,
        canMoveY: Boolean,
    ): Velocity {
        val vx =
            if (canMoveX) {
                velocity.x
            } else {
                getVx(
                    velocity = velocity,
                    backgroundManger = backgroundManger,
                )
            }

        val vy =
            if (canMoveY) {
                velocity.y
            } else {
                getVy(
                    velocity = velocity,
                    backgroundManger = backgroundManger,
                )
            }

        return Velocity(
            x = vx,
            y = vy,
        )
    }

    private fun getVy(
        velocity: Velocity,
        backgroundManger: BackgroundManager,
    ): Float {
        val dir = velocity.y.toDir()
        var section = Section(
            min = 0f,
            max = abs(velocity.y),
        )

        for (cnt: Int in 0..5) {
            section = getNewSection(
                backgroundManger = backgroundManger,
                dx = velocity.x,
                dy = section.average * dir,
                section = section,
            )
        }

        return section.min * dir
    }

    private fun getVx(
        velocity: Velocity,
        backgroundManger: BackgroundManager,
    ): Float {
        val dir = velocity.x.toDir()
        var section = Section(
            min = 0f,
            max = abs(velocity.x),
        )

        for (cnt: Int in 0..5) {
            section = getNewSection(
                backgroundManger = backgroundManger,
                dx = section.average * dir,
                dy = velocity.y,
                section = section,
            )
        }

        return section.min * dir
    }

    private fun getNewSection(
        backgroundManger: BackgroundManager,
        dx: Float,
        dy: Float,
        section: Section,
    ): Section {
        val square = playerSquare

        square.move(
            dx,
            dy,
        )

        return if (backgroundManger.isCollided(square)) {
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
