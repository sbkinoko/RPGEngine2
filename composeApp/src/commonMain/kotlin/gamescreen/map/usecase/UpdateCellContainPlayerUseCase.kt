package gamescreen.map.usecase

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository

/**
 * プレイヤーが入っているセルを更新
 */
class UpdateCellContainPlayerUseCase(
    private val playerPositionRepository: PlayerPositionRepository,
    private val playerCellRepository: PlayerCellRepository,
    private val backgroundRepository: BackgroundRepository,
) {
    operator fun invoke() {
        var playerIncludeCell: BackgroundCell? = null
        backgroundRepository.background.mapIndexed { _, rowArray ->
            rowArray.mapIndexed { _, cell ->
                cell.apply {
                    if (playerPositionRepository.getPlayerPosition().isIn(square)) {
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
