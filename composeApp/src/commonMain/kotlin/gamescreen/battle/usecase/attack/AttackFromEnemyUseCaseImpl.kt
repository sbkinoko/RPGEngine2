package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.repository.player.PlayerStatusRepository
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService

class AttackFromEnemyUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,

    private val findTargetService: FindTargetService,
    private val attackCalcService: AttackCalcService,
) : AttackUseCase {

    override suspend fun invoke(
        target: Int,
        attacker: StatusData,
        damageType: DamageType,
    ) {
        var actualTarget = target
        val players = playerStatusRepository.getPlayers()
        if (players[target].statusData.isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = players,
                target = target,
            )
        }

        val attacked = playerStatusRepository.getStatus(actualTarget)

        val damaged = attackCalcService.invoke(
            attacker = attacker,
            attacked = attacked.statusData,
            damageType = damageType,
        )

        playerStatusRepository.setStatus(
            id = actualTarget,
            status = attacked.copy(
                statusData = damaged,
            )
        )
    }
}
