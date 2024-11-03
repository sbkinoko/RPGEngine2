package core.confim.repository

import core.domain.Choice
import core.repository.command.CommandRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChoiceRepository : CommandRepository<List<Choice>> {
    override val commandTypeFlow: MutableSharedFlow<List<Choice>> = MutableSharedFlow(replay = 1)

    override val commandStateFlow: StateFlow<List<Choice>> = commandTypeFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    override val nowCommandType: List<Choice>
        get() = flg

    private var flg: List<Choice> = emptyList()
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                commandTypeFlow.emit(field)
            }
        }

    override fun pop() {
        flg = emptyList()
    }

    override fun push(commandType: List<Choice>) {
        flg = commandType
    }
}
