package main.repository.command

import kotlinx.coroutines.flow.MutableSharedFlow

interface CommandRepository<T> {
    val commandTypeFlow: MutableSharedFlow<T>
    val nowCommandType: T

    fun push(commandType: T)

    fun pop()
}
