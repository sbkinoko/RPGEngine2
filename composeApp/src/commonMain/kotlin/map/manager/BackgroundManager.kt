package map.manager

import kotlinx.coroutines.runBlocking
import map.data.LoopMap
import map.domain.BackgroundCell
import map.domain.MapData
import map.domain.Point
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

    val diffOfLoop: Float
    val allCellNum: Int = cellNum + 1

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

    var mapData: MapData = LoopMap()
        set(value) {
            loadMapData()
            field = value
        }

    init {
        diffOfLoop = cellSize * (allCellNum)
        this.mapData = mapData
        resetBackgroundCellPosition()
        loadMapData()
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
                            mapPoint = mapData.getMapPoint(
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
