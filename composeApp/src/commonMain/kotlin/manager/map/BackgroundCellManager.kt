package manager.map

import domain.map.BackgroundCell
import domain.map.MapPoint
import domain.map.Velocity

class BackgroundCellManager(
    val cellNum: Int,
    val sideLength: Int,
) {
    private val backgroundCellArray: Array<Array<BackgroundCell>>

    val diffOfLoop: Float
    val allCellNum: Int

    init {
        val cellSize = sideLength / cellNum.toFloat()
        allCellNum = cellNum + 1
        diffOfLoop = cellSize * (allCellNum)
        backgroundCellArray = Array(allCellNum) { row ->
            Array(allCellNum) { col ->
                BackgroundCell(
                    x = (col) * cellSize,
                    y = (row) * cellSize,
                    cellSize = cellSize,
                ).apply {
                    mapPoint = MapPoint(
                        x = col,
                        y = row,
                    )
                }
            }
        }
    }

    fun getCell(
        col: Int,
        row: Int,
    ): BackgroundCell {
        return backgroundCellArray[row][col]
    }

    fun moveBackgroundCell(
        velocity: Velocity,
    ) {
        moveBackgroundCell(
            dx = velocity.x,
            dy = velocity.y,
        )
    }

    fun moveBackgroundCell(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        backgroundCellArray.forEach { rowArray ->
            rowArray.forEach { bgCell ->
                bgCell.apply {
                    moveDisplayPoint(
                        dx = dx,
                        dy = dy,
                    )
                }
                checkLoop(bgCell = bgCell)
            }
        }
    }

    fun checkLoop(bgCell: BackgroundCell) {
        bgCell.apply {
            if (sideLength < square.leftSide) {
                square.move(
                    dx = -diffOfLoop,
                )
                mapPoint.x -= allCellNum
            } else if (square.rightSide < 0) {
                moveDisplayPoint(
                    dx = diffOfLoop,
                )
                mapPoint.x += allCellNum
            }

            if (sideLength < square.topSide) {
                moveDisplayPoint(
                    dy = -diffOfLoop,
                )
                mapPoint.y -= allCellNum
            } else if (square.bottomSide < 0) {
                moveDisplayPoint(
                    dy = diffOfLoop,
                )
                mapPoint.y += allCellNum
            }
        }
    }
}
