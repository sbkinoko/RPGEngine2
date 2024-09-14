package battle.usecase.attack

import battle.service.FindTargetService
import battle.service.updateparameter.UpdatePlayerStatusService
import common.repository.player.PlayerRepository

class AttackFromEnemyUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val findTargetService: FindTargetService,
    private val updatePlayerStatusService: UpdatePlayerStatusService,
) : AttackUseCase {

    override suspend fun invoke(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        val players = playerRepository.getPlayers()
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
