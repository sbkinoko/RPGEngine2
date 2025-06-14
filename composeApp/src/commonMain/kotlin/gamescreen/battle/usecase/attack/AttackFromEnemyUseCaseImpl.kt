package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService

class AttackFromEnemyUseCaseImpl(
    private val statusDataRepository: StatusDataRepository<StatusType.Player>,

    private val findTargetService: FindTargetService,
    private val attackCalcService: AttackCalcService,
) : AttackUseCase<StatusType.Enemy> {

    override suspend fun invoke(
        target: Int,
        attacker: StatusData<StatusType.Enemy>,
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
