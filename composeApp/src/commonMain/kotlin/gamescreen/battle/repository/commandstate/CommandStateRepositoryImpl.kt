package gamescreen.battle.repository.commandstate

import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.MainCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommandStateRepositoryImpl : CommandStateRepository {

    private var battleCommandTypeQueue: List<BattleCommandType> = listOf(MainCommand)
        set(value) {
            field = value
            mutableCommandStateFlow.value = field.last()
            CoroutineScope(Dispatchers.Default).launch {
                commandTypeFlow.emit(
                    field.last(),
                )
            }
        }

    override val nowCommandType: BattleCommandType
        get() = battleCommandTypeQueue.last()

    override val commandTypeFlow: MutableSharedFlow<BattleCommandType> =
        MutableSharedFlow(replay = 1)

    private val mutableCommandStateFlow =
        MutableStateFlow(nowCommandType)
    override val commandStateFlow: StateFlow<BattleCommandType> =
        mutableCommandStateFlow.asStateFlow()

    override fun init() {
        battleCommandTypeQueue = listOf(CommandStateRepository.INITIAL_COMMAND_STATE)
    }

    override fun push(commandType: BattleCommandType) {
        battleCommandTypeQueue = battleCommandTypeQueue + commandType
    }

    override fun pop() {
        // 大きさが1ならpopしない
        if (battleCommandTypeQueue.size == 1)
            return

        battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)
    }

    override fun popTo(condition: (BattleCommandType) -> Boolean) {
        // 大きさが1ならpopしない
        if (battleCommandTypeQueue.size == 1)
            return

        var tmpQueue = battleCommandTypeQueue.dropLast(1)

        while (
        // 条件を満たすcommandTypeまでさかのぼる
            condition(tmpQueue.last()).not() &&
            // もしくは大きさ1になるまで
            tmpQueue.size != 1
        ) {
            tmpQueue = tmpQueue.dropLast(1)
        }

        battleCommandTypeQueue = tmpQueue
    }
}
