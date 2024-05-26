package manager.map

import data.map.mapdata.LoopMap
import domain.map.BackgroundCell
import domain.map.MapData
import domain.map.MapPoint
import domain.map.Player
import domain.map.Square
import domain.map.Velocity

class BackgroundManager(
    val cellNum: Int,
    val sideLength: Int,
) {
    private var backgroundCellArray: Array<Array<BackgroundCell>>

    private val diffOfLoop: Float
    val allCellNum: Int

    private val fieldSquare: Square

    var mapData: MapData = LoopMap()
        set(value) {
            field = value
            loadMapData()
        }

    init {
        fieldSquare = Square(
            x = 0f,
            y = 0f,
            size = sideLength.toFloat(),
        )
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
        loadMapData()
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

    private fun checkLoop(bgCell: BackgroundCell) {
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

            mapPoint = getMapPoint(
                x = mapX,
                y = mapY,
            )

            imgID = mapData.getDataAt(mapPoint)
        }
    }

    private fun getMapPoint(x: Int, y: Int): MapPoint {
        return if (!mapData.isLoop) {
            MapPoint(
                x = x,
                y = y,
            )
        } else {
            MapPoint(
                x = collectX(x = x),
                y = collectY(y = y),
            )
        }
    }

    private fun collectX(x: Int): Int {
        return collectPoint(
            max = mapData.width,
            value = x
        )
    }

    private fun collectY(y: Int): Int {
        return collectPoint(
            max = mapData.height,
            value = y
        )
    }

    private fun collectPoint(max: Int, value: Int): Int {
        if (value < 0) {
            return value + max
        }
        if (max <= value) {
            return value - max
        }
        return value
    }

    private fun loadMapData() {
        backgroundCellArray = backgroundCellArray.mapIndexed { y, rowArray ->
            rowArray.mapIndexed { x, cell ->
                cell.apply {
                    imgID = mapData.getDataAt(
                        x = x,
                        y = y,
                    )
                }
            }.toTypedArray()
        }.toTypedArray()
    }

    fun findCellIncludePlayer(player: Player):BackgroundCell? {
        var playerIncludeCell:BackgroundCell? = null
        backgroundCellArray.mapIndexed { _, rowArray ->
            rowArray.mapIndexed { _, cell ->
                cell.apply {
                    if (player.square.isIn(square)) {
                        isPlayerIncludeCell = true
                        playerIncludeCell = this
                    } else {
                        isPlayerIncludeCell = false
                    }
                }
            }.toTypedArray()
        }.toTypedArray()
        return playerIncludeCell
    }
}
