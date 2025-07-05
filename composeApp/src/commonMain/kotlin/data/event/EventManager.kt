package data.event

import kotlinx.coroutines.flow.StateFlow

interface EventManager<T> {

    val eventFlag: StateFlow<Int>

    fun callEvent(
        key: T,
    )
}
