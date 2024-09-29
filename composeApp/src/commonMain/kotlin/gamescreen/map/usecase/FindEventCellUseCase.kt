package map.usecase

import map.domain.BackgroundCell
import map.repository.backgroundcell.BackgroundRepository
import map.repository.player.PlayerRepository
import map.repository.playercell.PlayerCellRepository

/**
 * プレイヤーが入っているセルを更新
 */
class FindEventCellUseCase(
    private val playerRepository: PlayerRepository,
    private val playerCellRepository: PlayerCellRepository,
    private val backgroundRepository: BackgroundRepository,
) {
    operator fun invoke() {
        var playerIncludeCell: BackgroundCell? = null
        backgroundRepository.background.mapIndexed { _, rowArray ->
            rowArray.mapIndexed { _, cell ->
                cell.apply {
                    if (playerRepository.getPlayerPosition().isIn(square)) {
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
