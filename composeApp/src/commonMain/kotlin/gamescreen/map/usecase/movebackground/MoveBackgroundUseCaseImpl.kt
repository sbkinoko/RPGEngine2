package gamescreen.map.usecase.movebackground

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.collision.list.GetCollisionListUseCase

class MoveBackgroundUseCaseImpl(
    private val backgroundRepository: BackgroundRepository,
    private val collisionListUseCase: GetCollisionListUseCase,
) : MoveBackgroundUseCase {

    override operator fun invoke(
        velocity: Velocity,
        backgroundData: BackgroundData,
        fieldSquare: NormalRectangle,
    ): BackgroundData {
        val background = backgroundData
            .fieldData
            .map { rowArray ->
                rowArray.map { bgCell ->
                    val moved = bgCell.move(
                        dx = velocity.x,
                        dy = velocity.y,
                    )

                    loopBackgroundCell(
                        bgCell = moved,
                        fieldSquare = fieldSquare,
                    )
                }
            }

        return BackgroundData(
            background
        )
    }

    // fixme 命名を考える
    /**
     * 背景を移動させたときに必要ならループさせる
     */
    private fun loopBackgroundCell(
        bgCell: BackgroundCell,
        fieldSquare: NormalRectangle,
    ): BackgroundCell {
        // loopに必要な移動量
        val allCellNum = backgroundRepository.allCellNum
        val diffOfLoop = allCellNum * backgroundRepository.cellSize

        // fixme ループで情報が変わるならnewで作る
        // それ以外の場合はcopyで作る

        //表示の移動量
        val dx: Float
        //移動後のマップのx座標
        val mapX: Int
        bgCell.apply {
            if (rectangle.isRight(fieldSquare)) {
                dx = -diffOfLoop
                mapX = mapPoint.x - allCellNum
            } else if (rectangle.isLeft(fieldSquare)) {
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
            if (rectangle.isDown(fieldSquare)) {
                dy = -diffOfLoop
                mapY = mapPoint.y - allCellNum
            } else if (rectangle.isUp(fieldSquare)) {
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
                collisionData = collisionListUseCase.invoke(
                    rectangle = bgCell.rectangle,
                    cellType = cellType,
                ),
                aroundCellId = backgroundRepository.getBackgroundAround(
                    mapPoint,
                )
            ).move(
                dx = dx,
                dy = dy,
            )
        }
    }
}
