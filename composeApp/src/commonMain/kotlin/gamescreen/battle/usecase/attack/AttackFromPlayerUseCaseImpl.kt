package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.repository.statusdata.StatusDataRepository
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.usecase.effect.EffectUseCase

class AttackFromPlayerUseCaseImpl(
    private val statusDataRepository: StatusDataRepository,
    private val findTargetService: FindTargetService,
    private val attackCalcService: AttackCalcService,
    private val effectUseCase: EffectUseCase,
) : AttackUseCase {
    override suspend operator fun invoke(
        target: Int,
        attacker: StatusData,
        damageType: DamageType,
    ) {
        var actualTarget = target
        val monsters = statusDataRepository.getStatusList()

        if (monsters[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters,
                target = target,
            )
        }

        val attacked = statusDataRepository.getStatusData(
            id = actualTarget,
        )

        val damaged = attackCalcService.invoke(
            attacker = attacker,
            attacked = attacked,
            damageType = damageType,
        )

        statusDataRepository.setStatusData(
            id = actualTarget,
            statusData = damaged,
        )

        effectUseCase.invoke(actualTarget)
    }
}
