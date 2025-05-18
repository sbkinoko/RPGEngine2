package gamescreen.map.usecase.updatecellcontainplayer

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.repository.playercell.PlayerCellRepository

class UpdateCellContainPlayerUseCaseImpl(
    private val playerCellRepository: PlayerCellRepository,
) : UpdateCellContainPlayerUseCase {

    override operator fun invoke(
        player: Player,
        backgroundData: BackgroundData,
    ): BackgroundCell? {
        var playerIncludeCell: BackgroundCell? = null
        lateinit var centerCell: BackgroundCell

        val square = player.square

        val center = (square as NormalRectangle).copy(
            width = 0f,
            height = 0f,
            point = square.point.copy(
                x = square.point.x + square.width / 2,
                y = square.point.y + square.height / 2,
            )
        )

        // プレイヤーを包含しているセルを保存
        backgroundData.fieldData
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

        return playerIncludeCell
    }
}
