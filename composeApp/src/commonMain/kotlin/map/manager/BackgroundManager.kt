package map.manager

import map.domain.BackgroundCell
import map.domain.Point
import map.domain.collision.Square
import map.repository.backgroundcell.BackgroundRepository
import map.repository.playercell.PlayerCellRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BackgroundManager : KoinComponent {
    private val repository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()

    val eventCell: BackgroundCell?
        get() {
            return playerCellRepository.playerIncludeCell
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
        var playerIncludeCell: BackgroundCell? = null
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
        playerCellRepository.playerIncludeCell = playerIncludeCell
    }
}
