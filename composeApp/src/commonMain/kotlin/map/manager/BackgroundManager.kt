package map.manager

import map.data.LoopMap
import map.domain.BackgroundCell
import map.domain.MapData
import map.domain.MapPoint
import map.domain.Point
import map.domain.Velocity
import map.domain.collision.Square

class BackgroundManager(
    val cellNum: Int,
    val sideLength: Int,
    mapData: MapData = LoopMap()
) {
    private lateinit var backgroundCellArray: Array<Array<BackgroundCell>>

    private val diffOfLoop: Float
    val allCellNum: Int

    private val fieldSquare: Square

    private val cellSize: Float

    private var playerIncludeCell: BackgroundCell? = null
    private var prePlayerIncludeCell: BackgroundCell? = null

    val eventCell: BackgroundCell?
        get() {
            if (playerIncludeCell == null)
                return null
            if (playerIncludeCell == prePlayerIncludeCell)
                return null

            return playerIncludeCell
        }

    private var mapData: MapData

    fun setMapData(mapData: MapData) {
        this.mapData = mapData
        loadMapData()
    }

    init {
        fieldSquare = Square(
            x = 0f,
            y = 0f,
            size = sideLength.toFloat(),
        )
        cellSize = sideLength / cellNum.toFloat()
        allCellNum = cellNum + 1
        diffOfLoop = cellSize * (allCellNum)
        this.mapData = mapData
        resetBackgroundCellPosition()
        loadMapData()
    }

    /**
     * 指定した位置のcellを取得する
     */
    fun getCell(
        col: Int,
        row: Int,
    ): BackgroundCell {
        return backgroundCellArray[row][col]
    }

    /**
     * 速度にしたがって背景を移動させる
     */
    fun moveBackgroundCell(
        velocity: Velocity,
    ) {
        moveBackgroundCell(
            dx = velocity.x,
            dy = velocity.y,
        )
    }

    /**
     * 速度にしたがって背景を移動させる
     */
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
                loopBackgroundCell(bgCell = bgCell)
            }
        }
    }

    /**
     * 表示領域の中心を返す
     */
    fun getCenterOfDisplay(): Point {
        return Point(
            x = sideLength / 2f,
            y = sideLength / 2f,
        )
    }

    /**
     * 背景を移動させたときに必要ならループさせる
     */
    private fun loopBackgroundCell(bgCell: BackgroundCell) {
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

    /**
     * 指定した位置の座標を取得する
     * ループしてるかどうかによって補正が異なる
     */
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

    /**
     * x軸に対して補正を行う
     */
    private fun collectX(x: Int): Int {
        return collectPoint(
            max = mapData.width,
            value = x
        )
    }

    /**
     * y軸に対して補正を行う
     */
    private fun collectY(y: Int): Int {
        return collectPoint(
            max = mapData.height,
            value = y
        )
    }

    /**
     * @param max 補正の最大値
     * @param value 補正したい値
     */
    private fun collectPoint(max: Int, value: Int): Int {
        if (value < 0) {
            return value + max
        }
        if (max <= value) {
            return value - max
        }
        return value
    }

    /**
     * 背景画像を読み込む
     */
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

    /**
     * プレイヤーが入っているセルを更新する
     */
    fun findCellIncludePlayer(playerSquare: Square) {
        prePlayerIncludeCell = playerIncludeCell
        playerIncludeCell = null
        backgroundCellArray.mapIndexed { _, rowArray ->
            rowArray.mapIndexed { _, cell ->
                cell.apply {
                    if (playerSquare.isIn(square)) {
                        isPlayerIncludeCell = true
                        playerIncludeCell = this
                    } else {
                        isPlayerIncludeCell = false
                    }
                }
            }.toTypedArray()
        }.toTypedArray()
    }

    /**
     * 背景画像の位置をリセットする
     */
    fun resetBackgroundCellPosition(
        mapX: Int = 0,
        mapY: Int = 0,
    ) {
        backgroundCellArray = Array(allCellNum) { row ->
            Array(allCellNum) { col ->
                BackgroundCell(
                    x = col * cellSize,
                    y = row * cellSize,
                    cellSize = cellSize,
                ).apply {
                    mapPoint = getMapPoint(
                        x = col - (cellNum - 1) / 2 + mapX,
                        y = row - (cellNum - 1) / 2 + mapY,
                    )
                    imgID = mapData.getDataAt(mapPoint)
                }
            }
        }
    }

    fun isCollided(player: Square): Boolean {
        backgroundCellArray.forEachIndexed { row, rowArray ->
            rowArray.forEachIndexed { col, cell ->
                if (cell.collisionList.isNotEmpty()) {
                    println("$col $row")
                    cell.collisionList.forEach {
                        println("bg")
                        (it as Square).printPosition()
                        println("player")
                        player.printPosition()
                        if (it.isOverlap(player)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}
