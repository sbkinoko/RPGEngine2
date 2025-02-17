package gamescreen.battle.usecase.condition

import core.domain.status.ConditionType
import core.repository.battlemonster.BattleInfoRepository
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService

class ConditionFromPlayerUseCaseImpl(
    private val battleInfoRepository: BattleInfoRepository,
    private val findTargetService: FindTargetService,
    private val updateMonsterStatusService: UpdateMonsterStatusUseCase,
) : ConditionUseCase {
    override suspend fun invoke(
        target: Int,
        conditionType: ConditionType
    ) {
        var actualTarget = target
        val monsters = battleInfoRepository.getMonsters()
        if (monsters[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters,
                target = target,
            )
        }

        updateMonsterStatusService.setCondition(
            id = actualTarget,
            conditionType = conditionType,
        )
    }
}
