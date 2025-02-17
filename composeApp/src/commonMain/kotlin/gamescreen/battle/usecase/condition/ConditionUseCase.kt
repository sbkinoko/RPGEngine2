package gamescreen.battle.usecase.condition

import core.domain.status.ConditionType

interface ConditionUseCase {
    suspend operator fun invoke(
        target: Int,
        conditionType: ConditionType,
    )
}
