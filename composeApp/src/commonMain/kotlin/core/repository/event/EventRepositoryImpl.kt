package core.repository.event

import core.domain.BattleResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventRepositoryImpl : EventRepository {
    private var winEvent = {}
    private var looseEvent = {}

    private val mutableBattleResultFlow = MutableStateFlow(
        BattleResult.None,
    )
    override val resultStateFlow: StateFlow<BattleResult>
        get() = mutableBattleResultFlow.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            resultStateFlow.collect {
                when (it) {
                    BattleResult.Win -> winEvent.invoke()
                    BattleResult.Lose -> looseEvent.invoke()
                    BattleResult.None -> Unit
                }
            }
        }
    }

    override fun setResult(result: BattleResult) {
        mutableBattleResultFlow.value = result
    }

    override fun setCallBack(
        winEvent: () -> Unit,
        looseEvent: () -> Unit,
    ) {
        this.winEvent = winEvent
        this.looseEvent = looseEvent
    }
}
