package gamescreen.map.usecase

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.Square
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.collision.CollisionRepository

class MoveBackgroundUseCase(
    private val repository: BackgroundRepository,
    private val collisionRepository: CollisionRepository,
) {
    suspend operator fun invoke(
        velocity: Velocity,
        fieldSquare: Square,
    ) {
        repository.setBackground(
            background = repository.backgroundStateFlow.value.map { rowArray ->
                rowArray.map { bgCell ->
                    bgCell.apply {
                        moveDisplayPoint(
                            dx = velocity.x,
                            dy = velocity.y,
                        )

                        loopBackgroundCell(
                            bgCell = this@apply,
                            fieldSquare = fieldSquare,
                        )
                    }
                }
            }
        )
    }

    /**
     * 背景を移動させたときに必要ならループさせる
     */
    private fun loopBackgroundCell(
        bgCell: BackgroundCell,
        fieldSquare: Square,
    ): BackgroundCell {
        // loopに必要な移動量
        val allCellNum = repository.allCellNum
        val diffOfLoop = allCellNum * repository.cellSize

        //移動後のマップのx座標
        val mapX: Int = bgCell.run {
            if (square.isRight(fieldSquare)) {
                square.move(
                    dx = -diffOfLoop,
                )
                mapPoint.x - allCellNum
            } else if (square.isLeft(fieldSquare)) {
                moveDisplayPoint(
                    dx = diffOfLoop,
                )
                mapPoint.x + allCellNum
            } else {
                mapPoint.x
            }
        }

        //移動後のマップのy座標
        val mapY: Int = bgCell.run {
            if (square.isDown(fieldSquare)) {
                moveDisplayPoint(
                    dy = -diffOfLoop,
                )
                mapPoint.y - allCellNum
            } else if (square.isUp(fieldSquare)) {
                moveDisplayPoint(
                    dy = diffOfLoop,
                )
                mapPoint.y + allCellNum
            } else {
                mapPoint.y
            }
        }

        repository.mapData.let {
            return bgCell.copy(
                mapPoint = it.getMapPoint(
                    x = mapX,
                    y = mapY,
                )
            ).apply {
                cellType = it.getDataAt(mapPoint)
                collisionList = collisionRepository.collisionData(
                    cellSize = cellSize,
                    square = square,
                    cellType = cellType,
                )
            }
        }
    }
}
