package gamescreen.map.usecase

import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.square.NormalSquare
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
        lateinit var centerCell: BackgroundCell

        val square = playerPositionRepository.getPlayerPosition()
            .square

        val center = (square as NormalSquare).copy(
            size = 0f,
            point = square.point.copy(
                x = square.point.x + square.size / 2,
                y = square.point.y + square.size / 2,
            )
        )
        // プレイヤーを包含しているセルを保存
        backgroundRepository
            .backgroundStateFlow
            .value
            .fieldData
            .map row@{ rowArray ->
                rowArray.map { cell ->
                    if (center.isIn(cell.square)) {
                        centerCell = cell
                    }

                    if (
                        square
                            .isIn(cell.square)
                    ) {
                        playerIncludeCell = cell
                        return@row
                    }
                }
            }

        playerCellRepository.playerIncludeCell = playerIncludeCell
        playerCellRepository.playerCenterCell = centerCell
    }
}
