package battle.usecase

import battle.repository.BattleMonsterRepository
import battle.service.AttackService
import battle.service.FindTargetService

class AttackUseCase(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val findTargetService: FindTargetService,
    private val attackService: AttackService

) {
    suspend operator fun invoke(
        target: Int,
        damage: Int,
    ) {
        var actualTarget = target
        val monsters = battleMonsterRepository.getMonsters()
        if (monsters[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                monsters = monsters,
                target = target,
            )
        }

        val afterMonster = attackService.attack(
            target = actualTarget,
            damage = damage,
            monsters = monsters
        )

        battleMonsterRepository.setMonster(
            monsters = afterMonster,
        )
    }
}
