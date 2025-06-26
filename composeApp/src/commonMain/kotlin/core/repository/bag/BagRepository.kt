package core.repository.bag

import core.domain.item.BagItemData
import data.item.tool.ToolId

interface BagRepository<T : ToolId> {
    fun getList(): List<BagItemData<T>>

    fun getItemIdAt(index: Int): T

    fun setData(data: BagItemData<T>)
}
