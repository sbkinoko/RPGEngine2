package core.repository.event

import core.domain.BattleResult
import kotlinx.coroutines.flow.StateFlow

class EventRepositoryImpl : EventRepository {
    override val resultStateFlow: StateFlow<BattleResult>
        get() = TODO("Not yet implemented")

    override fun setCallBack(
        winEvent: () -> Unit,
        looseEvent: () -> Unit,
    ) {
        TODO("Not yet implemented")
    }
}
