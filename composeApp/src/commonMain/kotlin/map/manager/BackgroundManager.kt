package map.manager

import map.domain.BackgroundCell
import map.domain.Point
import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BackgroundManager : KoinComponent {
    private val repository: BackgroundRepository by inject()

    val diffOfLoop: Float
        get() = cellSize * repository.allCellNum

    val allCellNum: Int
        get() = repository.allCellNum

    val cellSize: Float
        get() = repository.cellSize

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

    /**
     * 表示領域の中心を返す
     */
    fun getCenterOfDisplay(): Point {
        return Point(
            x = repository.screenSeize / 2f,
            y = repository.screenSeize / 2f,
        )
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
