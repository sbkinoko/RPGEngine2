package core.repository.command

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CommandRepository<T> {
    val commandTypeFlow: MutableSharedFlow<T>
    val commandStateFlow: StateFlow<T>
    val nowCommandType: T

    fun push(commandType: T)

    fun pop()
}
