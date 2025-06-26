package core.repository.bag

import core.domain.item.BagItemData
import data.item.tool.ToolId

class BagRepositoryImpl : BagRepository<ToolId> {
    private var bagToolDataList: List<BagItemData<ToolId>> = listOf()

    override fun getList(): List<BagItemData<ToolId>> {
        return bagToolDataList
    }

    override fun getItemIdAt(index: Int): ToolId {
        return bagToolDataList[index].id
    }

    override fun setData(data: BagItemData<ToolId>) {
        bagToolDataList = if (
            bagToolDataList.any { it.id == data.id }
        ) {
            // すでに同一のidがあれば更新
            bagToolDataList.map {
                if (it.id == data.id) {
                    data
                } else {
                    it
                }
            }
        } else {
            // ないので後ろに追加
            bagToolDataList + data
        }
    }
}
