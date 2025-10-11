package values.event

import androidx.compose.runtime.Stable

@Stable
sealed class EventType {
    data object None : EventType()

    data class Shop(
        val shopId: ShopId,
    ) : EventType()

    data class Box(
        val id: BoxId,
    ) : EventType()

    data class TalkEvent(
        val eventGroup: EventGroup,
    ) : EventType()

    data object Water : EventType()

    data object Ground : EventType()

    data object Ground1 : EventType()
    data object Ground2 : EventType()

    val canEvent: Boolean
        get() = this != None
}
