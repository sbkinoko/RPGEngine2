package battle.usecase.attack

import battle.service.FindTargetService
import core.repository.player.PlayerRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase

class AttackFromEnemyUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val findTargetService: FindTargetService,
    private val updatePlayerStatusService: UpdatePlayerStatusUseCase,
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
