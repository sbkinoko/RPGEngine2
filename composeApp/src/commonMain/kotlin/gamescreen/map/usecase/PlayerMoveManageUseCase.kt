package gamescreen.map.usecase

import gamescreen.map.domain.Player
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import kotlin.math.abs

// todo invokeにする
// todo interfaceを作成する
class PlayerMoveManageUseCase(
    private val isCollidedUseCase: IsCollidedUseCase,
) {
    private lateinit var player: Player

    /**
     * 移動可能な速度を返す
     */
    fun getMovableVelocity(
        player: Player,
        tentativePlayerVelocity: Velocity,
        backgroundData: BackgroundData,
    ): Velocity {
        this.player = player
        val square = player.square

        val moveBoth = square
            .move(
                dx = tentativePlayerVelocity.x,
                dy = tentativePlayerVelocity.y
            )

        // このままの速度で動けるなら移動
        if (isCollidedUseCase.invoke(
                playerSquare = moveBoth,
                backgroundData = backgroundData,
            ).not()
        ) {
            return tentativePlayerVelocity
        }

        //　x方向だけの移動ができるかチェック
        val onlyMoveX = square.move(
            dx = tentativePlayerVelocity.x,
            dy = 0f,
        )
        var canMoveX =
            isCollidedUseCase.invoke(
                onlyMoveX,
                backgroundData = backgroundData,
            ).not()

        //　y方向だけの移動ができるかチェック
        val onlyMoveY = player.square.move(
            dx = 0f,
            dy = tentativePlayerVelocity.y,
        )
        var canMoveY =
            isCollidedUseCase.invoke(
                onlyMoveY,
                backgroundData = backgroundData,
            ).not()

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
            backgroundData = backgroundData,
        )
    }

    private fun changeVelocity(
        velocity: Velocity,
        canMoveX: Boolean,
        canMoveY: Boolean,
        backgroundData: BackgroundData,
    ): Velocity {
        val vx =
            if (canMoveX) {
                velocity.x
            } else {
                getVx(
                    velocity = velocity,
                    backgroundData = backgroundData,
                )
            }

        val vy =
            if (canMoveY) {
                velocity.y
            } else {
                getVy(
                    velocity = velocity,
                    backgroundData = backgroundData,
                )
            }

        return Velocity(
            x = vx,
            y = vy,
        )
    }

    private fun getVy(
        velocity: Velocity,
        backgroundData: BackgroundData,
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
                backgroundData = backgroundData,
            )
        }

        return section.min * dir
    }

    private fun getVx(
        velocity: Velocity,
        backgroundData: BackgroundData,
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
                backgroundData = backgroundData,
            )
        }

        return section.min * dir
    }

    private fun getNewSection(
        dx: Float,
        dy: Float,
        section: Section,
        backgroundData: BackgroundData,
    ): Section {
        val square = player.square.move(
            dx = dx,
            dy = dy,
        )

        return if (isCollidedUseCase.invoke(
                square,
                backgroundData = backgroundData,
            )
        ) {
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
