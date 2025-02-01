package gamescreen.menu.repository.bag

import data.item.tool.ToolId
import gamescreen.menu.domain.BagToolData

interface BagRepository {
    fun getList(): List<BagToolData>

    fun getItemIdAt(index: Int): ToolId

    fun setData(data: BagToolData)
}
