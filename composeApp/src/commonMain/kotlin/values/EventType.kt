package values

import gamescreen.map.data.BoxId

sealed class EventType {
    data object None : EventType()

    class Box(
        val id: BoxId,
    ) : EventType()
}
