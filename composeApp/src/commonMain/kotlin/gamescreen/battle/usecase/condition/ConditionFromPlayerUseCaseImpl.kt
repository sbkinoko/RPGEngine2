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
        conditionType: ConditionType,
    ) {
        var actualTarget = target
        val monsters = battleInfoRepository.getStatusList()
        if (monsters[target].statusData.isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters.map { it.statusData },
                target = target,
            )
        }

        updateMonsterStatusService.addCondition(
            id = actualTarget,
            conditionType = conditionType,
        )
    }
}
