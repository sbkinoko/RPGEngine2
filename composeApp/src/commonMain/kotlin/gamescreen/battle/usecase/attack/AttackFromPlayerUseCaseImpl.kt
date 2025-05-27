package gamescreen.battle.usecase.attack

import core.domain.item.DamageType
import core.domain.status.StatusData
import core.repository.battlemonster.BattleInfoRepository
import gamescreen.battle.service.attackcalc.AttackCalcService
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.usecase.effect.EffectUseCase

class AttackFromPlayerUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
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
        val monsters = battleInfoRepository.getMonsters()

        if (monsters[target].statusData.isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters,
                target = target,
            )
        }

        val attacked = battleInfoRepository.getStatus(
            id = actualTarget,
        )

        val damaged = attackCalcService.invoke(
            attacker = attacker,
            attacked = attacked.statusData,
            damageType = damageType,
        )

        battleInfoRepository.setStatus(
            id = actualTarget,
            status = attacked.copy(
                statusData = damaged,
            )
        )

        effectUseCase.invoke(actualTarget)
    }
}
