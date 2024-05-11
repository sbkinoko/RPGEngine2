package manager.map

import domain.map.BackgroundCell
import domain.map.Point

class BackgroundCellManager(
    val cellNum: Int,
    sideLength: Int,
) {
    private val backgroundCellArray: Array<Array<BackgroundCell>>

    fun getCell(
        col: Int,
        row: Int
    ): BackgroundCell {
        return backgroundCellArray[row][col]
    }

    init {
        val cellSize = sideLength / cellNum.toFloat()
        backgroundCellArray = Array(cellNum) { row ->
            Array(cellNum) { col ->
                BackgroundCell().apply {
                    displayPoint = Point(
                        x = col * cellSize,
                        y = row * cellSize,
                    )
                    this.cellSize = cellSize
                }
            }
        }
    }

    fun moveBackgroundCell(
        dx: Float,
        dy: Float,
    ) {
        backgroundCellArray.forEach { rowArray ->
            rowArray.forEach { bgCell ->
                bgCell.moveDisplayPoint(
                    dx = dx,
                    dy = dy,
                )
            }
        }
    }
}
