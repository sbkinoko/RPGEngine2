package gamescreen.battle.usecase.attack

import core.repository.battlemonster.BattleInfoRepository
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.usecase.effect.EffectUseCase

class AttackFromPlayerUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
    private val findTargetService: FindTargetService,
    private val updateMonsterStatusService: UpdateMonsterStatusUseCase,
    private val effectUseCase: EffectUseCase,
) : AttackUseCase {
    override suspend operator fun invoke(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        val monsters = battleInfoRepository.getMonsters()
        if (monsters[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters,
                target = target,
            )
        }

        updateMonsterStatusService.decHP(
            id = actualTarget,
            amount = damage,
        )

        effectUseCase.invoke(actualTarget)
    }
}
