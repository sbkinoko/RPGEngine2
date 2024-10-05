package gamescreen.battle.repository.commandstate

import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.MainCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CommandStateRepositoryImpl : CommandStateRepository {
    override val commandTypeFlow: MutableSharedFlow<BattleCommandType> =
        MutableSharedFlow(replay = 1)
    override val nowCommandType: BattleCommandType
        get() = battleCommandTypeQueue.last()

    private var battleCommandTypeQueue: List<BattleCommandType> = listOf(MainCommand)

    override fun init() {
        battleCommandTypeQueue = listOf(CommandStateRepository.INITIAL_COMMAND_STATE)
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }

    override fun push(commandType: BattleCommandType) {
        battleCommandTypeQueue = battleCommandTypeQueue + commandType
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }

    override fun pop() {
        // 大きさが1ならpopしない
        if (battleCommandTypeQueue.size == 1)
            return

        battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }

    override fun popTo(condition: (BattleCommandType) -> Boolean) {
        // 大きさが1ならpopしない
        if (battleCommandTypeQueue.size == 1)
            return

        battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)


        while (
        // 条件を満たすcommandTypeまでさかのぼる
            condition(nowCommandType).not() &&
            // もしくは大きさ位置になるまで
            battleCommandTypeQueue.size != 1
        ) {
            battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)
        }

        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }
}
