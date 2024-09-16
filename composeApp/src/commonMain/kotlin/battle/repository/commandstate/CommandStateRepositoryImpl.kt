package battle.repository.commandstate

import battle.domain.BattleCommandType
import battle.domain.MainCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CommandStateRepositoryImpl : CommandStateRepository {
    override val battleCommandTypeFlow: MutableSharedFlow<BattleCommandType> =
        MutableSharedFlow(replay = 1)
    override val nowBattleCommandType: BattleCommandType
        get() = battleCommandTypeQueue.last()

    private var battleCommandTypeQueue: List<BattleCommandType> = listOf(MainCommand)

    override fun init() {
        battleCommandTypeQueue = listOf(CommandStateRepository.INITIAL_COMMAND_STATE)
        CoroutineScope(Dispatchers.Default).launch {
            battleCommandTypeFlow.emit(
                nowBattleCommandType,
            )
        }
    }

    override fun push(battleCommandType: BattleCommandType) {
        battleCommandTypeQueue = battleCommandTypeQueue + battleCommandType
        CoroutineScope(Dispatchers.Default).launch {
            battleCommandTypeFlow.emit(
                nowBattleCommandType,
            )
        }
    }

    override fun pop() {
        // 大きさが1ならpopしない
        if (battleCommandTypeQueue.size == 1)
            return

        battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)
        CoroutineScope(Dispatchers.Default).launch {
            battleCommandTypeFlow.emit(
                nowBattleCommandType,
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
            condition(nowBattleCommandType).not() &&
            // もしくは大きさ位置になるまで
            battleCommandTypeQueue.size != 1
        ) {
            battleCommandTypeQueue = battleCommandTypeQueue.dropLast(1)
        }

        CoroutineScope(Dispatchers.Default).launch {
            battleCommandTypeFlow.emit(
                nowBattleCommandType,
            )
        }
    }
}
