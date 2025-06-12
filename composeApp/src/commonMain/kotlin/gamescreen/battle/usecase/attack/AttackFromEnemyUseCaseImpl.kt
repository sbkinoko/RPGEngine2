package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService

class AttackFromEnemyUseCaseImpl(
    private val statusDataRepository: StatusDataRepository,

    private val findTargetService: FindTargetService,
    private val attackCalcService: AttackCalcService,
) : AttackUseCase {

    override suspend fun invoke(
        target: Int,
        attacker: StatusData,
        damageType: DamageType,
    ) {
        var actualTarget = target
        val players = statusDataRepository.getStatusList()
        if (players[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = players,
                target = target,
            )
        }

        val attacked = statusDataRepository.getStatusData(actualTarget)

        val damaged = attackCalcService.invoke(
            attacker = attacker,
            attacked = attacked,
            damageType = damageType,
        )

        statusDataRepository.setStatusData(
            id = actualTarget,
            statusData = damaged,
        )
    }
}
