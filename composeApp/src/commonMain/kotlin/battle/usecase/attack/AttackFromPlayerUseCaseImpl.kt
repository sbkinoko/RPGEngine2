package battle.usecase.attack

import battle.repository.battlemonster.BattleMonsterRepository
import battle.service.FindTargetService
import battle.service.attack.UpdateParameterService
import common.status.MonsterStatus

class AttackFromPlayerUseCaseImpl(
    private val battleMonsterRepository: BattleMonsterRepository,
    private val findTargetService: FindTargetService,
    private val attackMonsterService: UpdateParameterService<MonsterStatus>,
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

        val afterMonster = attackMonsterService.decHP(
            amount = damage,
            status = monsters[target],
        )

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
