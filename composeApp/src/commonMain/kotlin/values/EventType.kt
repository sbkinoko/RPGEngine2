package values

import gamescreen.map.data.BoxId
import gamescreen.map.data.ShopId

sealed class EventType {
    data object None : EventType()

    data object Talk : EventType()

    data class Shop(
        val shopId: ShopId,
    ) : EventType()

    class Box(
        val id: BoxId,
    ) : EventType()
}
