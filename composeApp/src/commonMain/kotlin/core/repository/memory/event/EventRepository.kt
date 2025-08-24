package core.repository.memory.event

import core.domain.BattleEventCallback
import core.domain.BattleResult
import kotlinx.coroutines.flow.StateFlow

interface EventRepository {
    val resultStateFlow: StateFlow<BattleResult>

    fun setResult(result: BattleResult)

    fun setCallBack(
        battleEventCallback: BattleEventCallback,
    )
}
