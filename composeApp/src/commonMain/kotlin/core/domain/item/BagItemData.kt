package core.domain.item

import data.item.ItemId

data class BagItemData<T : ItemId>(
    val id: T,
    val num: Int,
)
