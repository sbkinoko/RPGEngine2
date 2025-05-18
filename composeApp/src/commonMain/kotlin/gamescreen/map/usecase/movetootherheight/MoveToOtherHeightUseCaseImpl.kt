package gamescreen.map.usecase.movetootherheight

import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.Velocity
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import gamescreen.map.usecase.move.MoveUseCase
import kotlinx.coroutines.delay
import values.GameParams
import kotlin.math.abs

class MoveToOtherHeightUseCaseImpl(
    private val isCollidedUseCase: IsCollidedUseCase,
    private val moveUseCase: MoveUseCase,
) : MoveToOtherHeightUseCase {

    override suspend fun invoke(
        targetHeight: ObjectHeight,
        mapUiState: MapUiState,
        update: (MapUiState) -> Unit,
    ) {
        val heightUpdatedPlayer = mapUiState.run {
            copy(
                player = player.changeHeight(targetHeight)
            )
        }

        // 移動量を取得
        val (dx, dy) = calcMoveDistance(
            heightUpdatedPlayer,
        )

        // 実際に移動
        move(
            dx,
            dy,
            mapUiState = heightUpdatedPlayer,
            update = update,
        )
    }

    /**
     * 移動後に衝突判定がない最小距離を計算
     */
    private fun calcMoveDistance(
        mapUiState: MapUiState,
    ): Pair<Float, Float> {
        var maxDx = 0f
        var maxDy = 0f
        var minDx = 0f
        var minDy = 0f
        mapUiState.apply {
            player.run {
                val firstMove = size * 2

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
                        ),
                        backgroundData = backgroundData,
                        npcData = npcData,
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
                        ),
                        backgroundData = backgroundData,
                        npcData = npcData,
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
                            ),
                            backgroundData = backgroundData,
                            npcData = npcData,
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
                            ),
                            backgroundData = backgroundData,
                            npcData = npcData,
                        )
                    ) {
                        minDy = ave
                    } else {
                        maxDy = ave
                    }
                }
            }
        }

        return maxDx to maxDy
    }

    private suspend fun move(
        dx: Float,
        dy: Float,
        mapUiState: MapUiState,
        update: (MapUiState) -> Unit,
    ) {
        var restDx = dx
        var restDy = dy
        var updatedState = mapUiState

        while (true) {
            val velocity = Velocity(
                x = restDx,
                y = restDy,
            )

            restDx -= velocity.x
            restDy -= velocity.y

            if (velocity.isMoving.not()) {
                break
            }

            updatedState = moveUseCase.invoke(
                tentativeVelocity = velocity,
                actualVelocity = velocity,
                mapUiState = updatedState,
            )

            //　移動できることはわかっている
            //　のでプレイヤーの方向と移動方向に同じ物を入力
            update(
                updatedState,
            )
            delay(GameParams.DELAY)
        }
    }
}
