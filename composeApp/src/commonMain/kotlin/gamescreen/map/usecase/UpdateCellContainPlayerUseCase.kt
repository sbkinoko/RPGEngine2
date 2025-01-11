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

        // プレイヤーを包含しているセルを保存
        backgroundRepository
            .backgroundStateFlow
            .value
            .map row@{ rowArray ->
                rowArray.map { cell ->
                    if (
                        playerPositionRepository
                            .getPlayerPosition()
                            .isIn(cell.square)
                    ) {
                        playerIncludeCell = cell
                        return@row
                    }
                }
            }

        playerCellRepository.playerIncludeCell = playerIncludeCell
    }
}
