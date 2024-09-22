package core.confim.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import main.repository.command.CommandRepository

class ConfirmRepository : CommandRepository<Boolean> {
    override val commandTypeFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)
    override val nowCommandType: Boolean
        get() = flg

    private var flg = false

    override fun pop() {
        flg = false
        commandTypeFlow.tryEmit(false)
    }

    override fun push(commandType: Boolean) {
        flg = true
        commandTypeFlow.tryEmit(true)
    }
}
