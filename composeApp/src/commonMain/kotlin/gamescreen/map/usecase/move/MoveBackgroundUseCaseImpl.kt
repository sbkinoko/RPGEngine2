package gamescreen.map.usecase.move

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.moveDisplayPoint
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.npc.NPCRepository

class MoveBackgroundUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val npcRepository: NPCRepository,
) : MoveBackgroundUseCase {

    override suspend operator fun invoke(
        velocity: Velocity,
        fieldSquare: NormalSquare,
    ) {
        val background = backgroundRepository
            .backgroundStateFlow
            .value
            .map { rowArray ->
                rowArray.map { bgCell ->
                    val moved = bgCell.moveDisplayPoint(
                        dx = velocity.x,
                        dy = velocity.y,
                    )

                    loopBackgroundCell(
                        bgCell = moved,
                        fieldSquare = fieldSquare,
                    )
                }
            }

        backgroundRepository.setBackground(
            background = background,
        )

        val npc = npcRepository.npcStateFlow.value.map {
            it.copy(
                square = it.square.move(
                    dx = velocity.x,
                    dy = velocity.y,
                )
            )
        }

        npcRepository.setNpc(
            npc
        )
    }

    // fixme 命名を考える
    /**
     * 背景を移動させたときに必要ならループさせる
     */
    private fun loopBackgroundCell(
        bgCell: BackgroundCell,
        fieldSquare: NormalSquare,
    ): BackgroundCell {
        // loopに必要な移動量
        val allCellNum = backgroundRepository.allCellNum
        val diffOfLoop = allCellNum * backgroundRepository.cellSize

        //表示の移動量
        val dx: Float
        //移動後のマップのx座標
        val mapX: Int
        bgCell.apply {
            if (square.isRight(fieldSquare)) {
                dx = -diffOfLoop
                mapX = mapPoint.x - allCellNum
            } else if (square.isLeft(fieldSquare)) {
                dx = diffOfLoop
                mapX = mapPoint.x + allCellNum
            } else {
                dx = 0f
                mapX = mapPoint.x
            }
        }

        //表示の移動量
        val dy: Float
        //移動後のマップのy座標
        val mapY: Int
        bgCell.apply {
            if (square.isDown(fieldSquare)) {
                dy = -diffOfLoop
                mapY = mapPoint.y - allCellNum
            } else if (square.isUp(fieldSquare)) {
                dy = diffOfLoop
                mapY = mapPoint.y + allCellNum
            } else {
                dy = 0f
                mapY = mapPoint.y
            }
        }

        backgroundRepository.mapData.let {
            val mapPoint = it.getMapPoint(
                x = mapX,
                y = mapY,
            )
            val cellType = it.getDataAt(mapPoint)

            return bgCell.copy(
                mapPoint = mapPoint,
                cellType = cellType,
            ).moveDisplayPoint(
                dx = dx,
                dy = dy,
            )
        }
    }
}
