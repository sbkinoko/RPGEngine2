package map.manager

import kotlinx.coroutines.runBlocking
import map.domain.BackgroundCell
import map.domain.Point
import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BackgroundManager(
    val cellNum: Int,
    val sideLength: Int,
) : KoinComponent {
    private val repository: BackgroundRepository by inject()

    val diffOfLoop: Float
    val allCellNum: Int = cellNum + 1

    val cellSize: Float = sideLength / cellNum.toFloat()

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

    init {
        diffOfLoop = cellSize * (allCellNum)
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
                            imgID = repository.mapData.getDataAt(
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
}
