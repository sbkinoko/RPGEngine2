package gamescreen.map.usecase

import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.square.NormalRectangle
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
    // fixme プレイヤーの位置と背景の位置は外から入れる
    operator fun invoke() {
        var playerIncludeCell: BackgroundCell? = null
        lateinit var centerCell: BackgroundCell

        val square = playerPositionRepository.getPlayerPosition()
            .square

        val center = (square as NormalRectangle).copy(
            width = 0f,
            height = 0f,
            point = square.point.copy(
                x = square.point.x + square.width / 2,
                y = square.point.y + square.height / 2,
            )
        )
        // プレイヤーを包含しているセルを保存
        backgroundRepository
            .backgroundStateFlow
            .value
            .fieldData
            .map row@{ rowArray ->
                rowArray.map { cell ->
                    if (center.isIn(cell.rectangle)) {
                        centerCell = cell
                    }

                    if (
                        square
                            .isIn(cell.rectangle)
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
