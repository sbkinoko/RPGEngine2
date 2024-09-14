package battle.usecase.attack

import battle.service.FindTargetService
import battle.service.attack.UpdateParameterService
import common.repository.player.PlayerRepository
import common.status.PlayerStatus

class AttackFromEnemyUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val attackPlayerService: UpdateParameterService<PlayerStatus>,
    private val findTargetService: FindTargetService,
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

        val afterPlayer = attackPlayerService.decHP(
            amount = damage,
            status = players[target]
        )

        playerRepository.setPlayer(
            id = actualTarget,
            status = afterPlayer
        )
    }
}
