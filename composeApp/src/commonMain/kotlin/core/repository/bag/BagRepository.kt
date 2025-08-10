package core.repository.bag

import core.domain.item.BagItemData
import data.repository.item.ItemId

interface BagRepository<T : ItemId> {
    fun getList(): List<BagItemData<T>>

    fun getItemIdAt(index: Int): T

    fun setData(data: BagItemData<T>)
}
