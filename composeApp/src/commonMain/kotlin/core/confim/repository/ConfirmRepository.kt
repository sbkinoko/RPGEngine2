package core.confim.repository

import core.repository.command.CommandRepository
import kotlinx.coroutines.flow.MutableSharedFlow

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
        if (!commandType) {
            throw RuntimeException("Confirmは必ずtrueを入れる")
        }

        flg = true
        commandTypeFlow.tryEmit(true)
    }
}
