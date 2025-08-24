package gamescreen.map.usecase.save

import core.repository.memory.money.MoneyRepository
import core.repository.storage.MoneyDBRepository
import gamescreen.map.domain.Player
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.repository.position.PositionRepository

class SaveUseCaseImpl(
    private val playerCellRepository: PlayerCellRepository,
    private val positionRepository: PositionRepository,
    private val backgroundRepository: BackgroundRepository,

    private val moneyRepository: MoneyRepository,
    private val moneyDBRepository: MoneyDBRepository,
) : SaveUseCase {
    override fun save(
        player: Player,
    ) {
        val playerCell = playerCellRepository.playerCenterCell

        positionRepository.save(
            x = playerCell.mapPoint.x,
            y = playerCell.mapPoint.y,
            playerDx = player.square.centerHorizontal - playerCell.centerHorizontal,
            playerDy = player.square.centerVertical - playerCell.centerVertical,
            objectHeight = player.square.objectHeight,
            mapData = backgroundRepository.mapData,
        )

        moneyDBRepository.set(
            moneyRepository.moneyStateFLow.value
        )
    }
}
