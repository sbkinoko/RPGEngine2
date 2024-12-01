package gamescreen.battle.usecase.attack

import core.repository.player.PlayerStatusRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService

class AttackFromEnemyUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val findTargetService: FindTargetService,
    private val updatePlayerStatusService: UpdatePlayerStatusUseCase,
) : AttackUseCase {

    override suspend fun invoke(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        val players = playerStatusRepository.getPlayers()
        if (players[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = players,
                target = target,
            )
        }

        updatePlayerStatusService.decHP(
            id = actualTarget,
            amount = damage,
        )
    }
}
