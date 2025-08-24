package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService

class AttackUseCaseImpl(
    private val statusDataRepository: core.repository.memory.character.statusdata.StatusDataRepository,

    private val findTargetService: FindTargetService,
    private val attackCalcService: AttackCalcService,
) : AttackUseCase {

    override suspend fun invoke(
        target: Int,
        attacker: StatusData,
        damageType: DamageType,
        effect: (Int) -> Unit,
    ) {
        var actualTarget = target
        val targets = statusDataRepository.getStatusList()

        if (targets[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = targets,
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

        effect.invoke(actualTarget)
    }
}
