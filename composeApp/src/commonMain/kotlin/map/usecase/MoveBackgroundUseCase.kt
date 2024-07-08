package map.usecase

import map.domain.BackgroundCell
import map.domain.MapData
import map.domain.Velocity
import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MoveBackgroundUseCase : KoinComponent {
    private val repository: BackgroundRepository by inject()
    operator fun invoke(
        velocity: Velocity,
        fieldSquare: Square,
        diffOfLoop: Float,
        allCellNum: Int,
        mapData: MapData,
    ) {
        repository.background.forEach { rowArray ->
            rowArray.forEach { bgCell ->
                bgCell.apply {
                    moveDisplayPoint(
                        dx = velocity.x,
                        dy = velocity.y,
                    )
                }
                loopBackgroundCell(
                    bgCell = bgCell,
                    fieldSquare = fieldSquare,
                    diffOfLoop = diffOfLoop,
                    allCellNum = allCellNum,
                    mapData = mapData,
                )
            }
        }
    }

    /**
     * 背景を移動させたときに必要ならループさせる
     */
    private fun loopBackgroundCell(
        bgCell: BackgroundCell,
        fieldSquare: Square,
        diffOfLoop: Float,
        allCellNum: Int,
        mapData: MapData,
    ) {
        bgCell.apply {
            val mapX: Int =
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

            val mapY: Int =
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

            mapPoint = mapData.getMapPoint(
                x = mapX,
                y = mapY,
            )

            imgID = mapData.getDataAt(mapPoint)
        }
    }
}
