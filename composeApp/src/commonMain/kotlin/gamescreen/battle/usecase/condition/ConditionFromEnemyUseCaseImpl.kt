package gamescreen.battle.usecase.condition

import core.domain.status.ConditionType
import core.repository.statusdata.StatusDataRepository
import core.usecase.updateparameter.UpdateStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService

class ConditionFromEnemyUseCaseImpl(
    private val statusDataRepository: StatusDataRepository,
    private val findTargetService: FindTargetService,
    private val updatePlayerStatusService: UpdateStatusUseCase,
) : ConditionUseCase {
    override suspend fun invoke(
        target: Int,
        conditionType: ConditionType,
    ) {
        var actualTarget = target
        val players = statusDataRepository.getStatusList()
        if (players[target].isActive.not()) {
            actualTarget = findTargetService.findNext(
                statusList = players,
                target = target,
            )
        }

        updatePlayerStatusService.addCondition(
            id = actualTarget,
            conditionType = conditionType,
        )
    }
}
