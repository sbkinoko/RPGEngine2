package gamescreen.battle.usecase.condition

import core.domain.status.ConditionType
import core.repository.player.PlayerStatusRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.battle.service.findtarget.FindTargetService

class ConditionFromEnemyUseCaseImpl(
    private val playerStatusRepository: PlayerStatusRepository,
    private val findTargetService: FindTargetService,
    private val updatePlayerStatusService: UpdatePlayerStatusUseCase,
) : ConditionUseCase {
    override suspend fun invoke(
        target: Int,
        conditionType: ConditionType,
    ) {
        var actualTarget = target
        val players = playerStatusRepository.getStatusList()
        if (players[target].statusData.isActive.not()) {
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
