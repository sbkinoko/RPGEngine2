package core.domain.item

import data.repository.monster.item.ItemId

data class BagItemData<T : ItemId>(
    val id: T,
    val num: Int,
)
