package values.event

sealed class EventType {
    data object None : EventType()

    data class Shop(
        val shopId: ShopId,
    ) : EventType()

    data class Box(
        val id: BoxId,
    ) : EventType()

    data object Water : EventType()

    data object Ground : EventType()

    val canEvent: Boolean
        get() = this != None
}
