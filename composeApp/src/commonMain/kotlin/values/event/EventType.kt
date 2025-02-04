package values.event

sealed class EventType {
    data object None : EventType()

    data class Shop(
        val shopId: ShopId,
    ) : EventType()

    class Box(
        val id: BoxId,
    ) : EventType()

    val canEvent: Boolean
        get() = this != None
}
