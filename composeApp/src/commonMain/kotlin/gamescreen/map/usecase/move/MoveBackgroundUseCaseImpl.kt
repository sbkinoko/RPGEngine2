package gamescreen.map.usecase.move

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.Square
import gamescreen.map.domain.moveDisplayPoint
import gamescreen.map.repository.backgroundcell.BackgroundRepository

class MoveBackgroundUseCaseImpl(
    private val repository: BackgroundRepository,
) : MoveBackgroundUseCase {

    override suspend operator fun invoke(
        velocity: Velocity,
        fieldSquare: Square,
    ) {
        val background = repository
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

        repository.setBackground(
            background = background,
        )
    }

    // fixme 命名を考える
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

        repository.mapData.let {
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
