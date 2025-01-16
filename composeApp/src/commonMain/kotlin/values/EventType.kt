package values

import gamescreen.map.data.BoxId

sealed class EventType {
    data object None : EventType()

    data object Talk : EventType()

    data object Shop : EventType()

    class Box(
        val id: BoxId,
    ) : EventType()
}
