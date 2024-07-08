package map.manager

import kotlinx.coroutines.runBlocking
import map.data.LoopMap
import map.domain.BackgroundCell
import map.domain.MapData
import map.domain.MapPoint
import map.domain.Point
import map.domain.Velocity
import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BackgroundManager(
    val cellNum: Int,
    val sideLength: Int,
    mapData: MapData = LoopMap()
) : KoinComponent {
    private val repository: BackgroundRepository by inject()

    private val diffOfLoop: Float
    val allCellNum: Int = cellNum + 1

    private val fieldSquare: Square = Square(
        x = 0f,
        y = 0f,
        size = sideLength.toFloat(),
    )

    private val cellSize: Float = sideLength / cellNum.toFloat()

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
        return repository.getBackgroundAt(
            x = col,
            y = row,
        )
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
        repository.background.forEach { rowArray ->
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
        runBlocking {
            repository.setBackground(
                repository.background.mapIndexed { y, rowArray ->
                    rowArray.mapIndexed { x, cell ->
                        cell.apply {
                            imgID = mapData.getDataAt(
                                x = x,
                                y = y,
                            )
                        }
                    }
                }
            )
        }
    }

    /**
     * プレイヤーが入っているセルを更新する
     */
    fun findCellIncludePlayer(playerSquare: Square) {
        prePlayerIncludeCell = playerIncludeCell
        playerIncludeCell = null
        repository.background.mapIndexed { _, rowArray ->
            rowArray.mapIndexed { _, cell ->
                cell.apply {
                    if (playerSquare.isIn(square)) {
                        isPlayerIncludeCell = true
                        playerIncludeCell = this
                    } else {
                        isPlayerIncludeCell = false
                    }
                }
            }
        }
    }

    /**
     * 背景画像の位置をリセットする
     */
    fun resetBackgroundCellPosition(
        mapX: Int = 0,
        mapY: Int = 0,
    ) {
        runBlocking {
            repository.setBackground(
                List(allCellNum) { row ->
                    List(allCellNum) { col ->
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
            )
        }
    }
}
