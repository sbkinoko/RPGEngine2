package values

sealed class EventType {
    data object None : EventType()

    class Box(
        val id: Int,
    ) : EventType()
}
