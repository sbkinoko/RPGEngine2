package gamescreen.battle.repository.commandstate

import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.MainCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommandStateRepositoryImpl : CommandStateRepository {

    private var battleCommandTypeQueue: List<BattleCommandType> = listOf(MainCommand)
        set(value) {
            field = value
            mutableCommandStateFlow.value = field.last()
        }

    override val nowBattleCommandType: BattleCommandType
        get() = battleCommandTypeQueue.last()


    private val mutableCommandStateFlow =
        MutableStateFlow(nowBattleCommandType)
    override val commandStateFlow: StateFlow<BattleCommandType> =
        mutableCommandStateFlow.asStateFlow()

    override fun init() {
        battleCommandTypeQueue = listOf(CommandStateRepository.INITIAL_COMMAND_STATE)
    }

    override fun push(battleCommandType: BattleCommandType) {
        battleCommandTypeQueue = battleCommandTypeQueue + battleCommandType
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
