package core.domain.item

import data.repository.item.ItemId

data class BagItemData<T : ItemId>(
    val id: T,
    val num: Int,
)
