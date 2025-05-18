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

    //todo callbackでUIを更新するようにする
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
            mapUiState,
        )

        // 実際に移動
        move(
            dx,
            dy,
            mapUiState = mapUiState,
        )

        // fixme 暫定処理
        // callbackを完成させたら削除
        update(heightUpdatedPlayer)
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
    ) {
        var restDx = dx
        var restDy = dy

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

            // fixme ここでUI更新処理を呼び出す
            //　移動できることはわかっている
            //　のでプレイヤーの方向と移動方向に同じ物を入力
            moveUseCase.invoke(
                tentativeVelocity = velocity,
                actualVelocity = velocity,
                mapUiState = mapUiState,
            )
            delay(GameParams.DELAY)
        }
    }
}
