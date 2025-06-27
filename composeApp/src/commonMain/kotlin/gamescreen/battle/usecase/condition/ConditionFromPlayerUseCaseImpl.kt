package gamescreen.battle.usecase.condition

import core.domain.status.ConditionType
import core.repository.statusdata.StatusDataRepository
import core.usecase.updateparameter.UpdateStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService

class ConditionFromPlayerUseCaseImpl(
    private val statusDataRepository: StatusDataRepository,
    private val findTargetService: FindTargetService,
    private val updateMonsterStatusUseCase: UpdateStatusUseCase,
) : ConditionUseCase {
    override suspend fun invoke(
        target: Int,
        conditionType: ConditionType,
    ) {
        var actualTarget = target
        val monsters = statusDataRepository.getStatusList()
        if (monsters[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = monsters,
                target = target,
            )
        }

        updateMonsterStatusUseCase.addCondition(
            id = actualTarget,
            conditionType = conditionType,
        )
    }
}
