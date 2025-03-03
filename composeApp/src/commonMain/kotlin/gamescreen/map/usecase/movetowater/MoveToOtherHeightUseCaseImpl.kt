package gamescreen.map.usecase.movetowater

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import gamescreen.map.usecase.move.MoveUseCase
import kotlinx.coroutines.delay
import values.GameParams
import kotlin.math.abs

class MoveToOtherHeightUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
    private val isCollidedUseCase: IsCollidedUseCase,
    private val moveUseCase: MoveUseCase,
) : MoveToOtherHeightUseCase {

    override suspend fun invoke(
        targetHeight: ObjectHeight,
    ) {
        val player = playerPositionRepository.getPlayerPosition()

        val heightUpdatedPlayer = player.copy(
            square = (player.square as NormalSquare).copy(
                objectHeight = targetHeight,
            )
        )

        var maxDx = 0f
        var maxDy = 0f
        var minDx = 0f
        var minDy = 0f
        heightUpdatedPlayer.run {
            val firstMove = heightUpdatedPlayer.size * 2

            when (dir) {
                PlayerDir.UP -> maxDy = -firstMove

                PlayerDir.DOWN -> maxDy = firstMove

                PlayerDir.LEFT -> maxDx = -firstMove

                PlayerDir.RIGHT -> maxDx = firstMove

                PlayerDir.NONE -> Unit
            }

            if (isCollidedUseCase(
                    square.move(
                        dx = maxDx,
                        dy = maxDy,
                    )
                )
            ) {
                when (dir) {
                    PlayerDir.UP,
                    PlayerDir.DOWN,
                    ->
                        maxDx = firstMove

                    PlayerDir.LEFT,
                    PlayerDir.RIGHT,
                    ->
                        maxDy = firstMove

                    PlayerDir.NONE -> Unit
                }
            }

            if (isCollidedUseCase(
                    square.move(
                        dx = maxDx,
                        dy = maxDy,
                    )
                )
            ) {
                when (dir) {
                    PlayerDir.UP,
                    PlayerDir.DOWN,
                    ->
                        maxDx = -firstMove

                    PlayerDir.LEFT,
                    PlayerDir.RIGHT,
                    ->
                        maxDy = -firstMove

                    PlayerDir.NONE -> Unit
                }
            }

            while (abs(minDx - maxDx) > 1) {
                val ave = (minDx + maxDx) / 2
                if (isCollidedUseCase(
                        square.move(
                            dx = ave,
                            dy = maxDy,
                        )
                    )
                ) {
                    minDx = ave
                } else {
                    maxDx = ave
                }
            }

            while (abs(minDy - maxDy) > 1) {
                val ave = (minDy + maxDy) / 2
                if (isCollidedUseCase(
                        square.move(
                            dx = maxDx,
                            dy = ave,
                        )
                    )
                ) {
                    minDy = ave
                } else {
                    maxDy = ave
                }
            }
        }

        // 高さを保存
        playerPositionRepository.setPlayerPosition(
            heightUpdatedPlayer,
        )

        while (true) {
            val velocity = Velocity(
                x = maxDx,
                y = maxDy,
            )

            maxDx -= velocity.x
            maxDy -= velocity.y

            if (velocity.isMoving.not()) {
                break
            }

            //　移動できることはわかっている
            //　のでプレイヤーの方向と移動方向に同じ物を入力
            moveUseCase.invoke(
                tentativeVelocity = velocity,
                actualVelocity = velocity,
            )
            delay(GameParams.DELAY)
        }
    }
}
