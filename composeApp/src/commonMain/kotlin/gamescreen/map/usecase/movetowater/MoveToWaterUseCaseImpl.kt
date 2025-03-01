package gamescreen.map.usecase.movetowater

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import kotlin.math.abs

class MoveToWaterUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
    private val isCollidedUseCase: IsCollidedUseCase,
) : MoveToWaterUseCase {

    override suspend fun invoke() {
        val player = playerPositionRepository.getPlayerPosition()

        val waterPlayer = player.copy(
            square = (player.square as NormalSquare).copy(
                objectHeight = ObjectHeight.Water
            )
        )

        var maxDx = 0f
        var maxDy = 0f
        var minDx = 0f
        var minDy = 0f
        waterPlayer.run {
            val firstMove = waterPlayer.size * 2

            when (waterPlayer.dir) {
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
                when (waterPlayer.dir) {
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
                when (waterPlayer.dir) {
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

        playerPositionRepository.setPlayerPosition(
            waterPlayer.copy(
                square = waterPlayer.square.move(
                    dx = maxDx,
                    dy = maxDy,
                )
            ),
        )
    }
}
