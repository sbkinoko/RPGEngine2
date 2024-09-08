package battle.usecase.attack

import battle.repository.battlemonster.BattleMonsterRepository
import battle.service.FindTargetService
import battle.service.attack.DecHpService
import common.status.MonsterStatus

class AttackFromPlayerUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val findTargetService: FindTargetService,
    private val attackMonsterService: DecHpService,
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

        val afterMonster = attackMonsterService.attack(
            target = actualTarget,
            damage = damage,
            status = monsters[target],
        ) as MonsterStatus

        battleMonsterRepository.setMonsters(
            monsters = monsters.mapIndexed { index, monsterStatus ->
                if (index != actualTarget) {
                    monsterStatus
                } else {
                    afterMonster
                }
            }
        )
    }
}
