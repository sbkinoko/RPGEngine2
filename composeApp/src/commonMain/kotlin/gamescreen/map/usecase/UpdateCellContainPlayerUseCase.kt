package gamescreen.map.usecase

import gamescreen.map.domain.BackgroundCell
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        //fixme PlayerCellRepositoryImplを修正したら削除する
        // プレイヤーを包含しているセルを更新する
        val background = backgroundRepository
            .backgroundStateFlow
            .value
            .map { rowArray ->
                rowArray.map { cell ->
                    if (
                        playerPositionRepository
                            .getPlayerPosition()
                            .isIn(cell.square)
                    ) {
                        cell.copy(
                            isPlayerIncludeCell = true,
                        )
                    } else {
                        cell.copy(
                            isPlayerIncludeCell = false,
                        )
                    }
                }
            }

        CoroutineScope(Dispatchers.Default).launch {
            backgroundRepository.setBackground(
                background = background
            )
        }
        playerCellRepository.playerIncludeCell = playerIncludeCell
    }
}
