package core.repository.event

import common.DefaultScope
import core.domain.BattleEventCallback
import core.domain.BattleResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventRepositoryImpl : EventRepository {
    private var battleEventCallback: BattleEventCallback = BattleEventCallback.default

    private val mutableBattleResultFlow = MutableStateFlow(
        BattleResult.None,
    )
    override val resultStateFlow: StateFlow<BattleResult>
        get() = mutableBattleResultFlow.asStateFlow()

    init {
        DefaultScope.launch {
            resultStateFlow.collect {
                battleEventCallback.callback(it)
            }
        }
    }

    override fun setResult(result: BattleResult) {
        mutableBattleResultFlow.value = result
    }

    override fun setCallBack(
        battleEventCallback: BattleEventCallback,
    ) {
        this.battleEventCallback = battleEventCallback
    }
}
