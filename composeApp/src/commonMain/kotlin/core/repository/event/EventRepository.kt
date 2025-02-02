package core.repository.event

import core.domain.BattleResult
import kotlinx.coroutines.flow.StateFlow

interface EventRepository {
    val resultStateFlow: StateFlow<BattleResult>

    fun setCallBack(
        winEvent: () -> Unit,
        looseEvent: () -> Unit,
    )
}
