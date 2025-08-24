
import core.domain.status.ConditionType
import core.usecase.updateparameter.UpdateStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService
import gamescreen.battle.usecase.condition.ConditionUseCase

class ConditionUseCaseImpl(
    private val statusDataRepository: core.repository.memory.character.statusdata.StatusDataRepository,
    private val findTargetService: FindTargetService,
    private val updateStatusUseCase: UpdateStatusUseCase,
) : ConditionUseCase {
    override suspend fun invoke(
        target: Int,
        conditionType: ConditionType,
    ) {
        var actualTarget = target
        val targets = statusDataRepository.getStatusList()
        if (targets[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = targets,
                target = target,
            )
        }

        updateStatusUseCase.addCondition(
            id = actualTarget,
            conditionType = conditionType,
        )
    }
}
