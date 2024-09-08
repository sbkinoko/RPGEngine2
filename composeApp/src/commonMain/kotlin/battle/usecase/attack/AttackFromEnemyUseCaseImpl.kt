package battle.usecase.attack

import battle.service.FindTargetService
import battle.service.attack.DecHpService
import common.repository.player.PlayerRepository
import common.status.PlayerStatus

class AttackFromEnemyUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val attackPlayerService: DecHpService,
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

        val afterPlayer = attackPlayerService.attack(
            target = actualTarget,
            damage = damage,
            status = players[target]
        ) as PlayerStatus

        playerRepository.setPlayer(
            id = actualTarget,
            status = afterPlayer
        )
    }
}
