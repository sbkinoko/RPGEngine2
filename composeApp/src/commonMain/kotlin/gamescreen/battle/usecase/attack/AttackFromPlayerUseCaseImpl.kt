package gamescreen.battle.usecase.attack

import core.repository.battlemonster.BattleMonsterRepository
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import gamescreen.battle.service.FindTargetService

class AttackFromPlayerUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val findTargetService: FindTargetService,
    private val updateMonsterStatusService: UpdateMonsterStatusUseCase
) : AttackUseCase {
    override suspend operator fun invoke(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        val monsters = battleMonsterRepository.getMonsters()
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
    }
}
