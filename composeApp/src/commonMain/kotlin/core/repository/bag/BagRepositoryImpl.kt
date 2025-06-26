package core.repository.bag

import core.domain.item.BagItemData
import data.item.ItemId

class BagRepositoryImpl<T : ItemId> : BagRepository<T> {
    private var bagToolDataList: List<BagItemData<T>> = listOf()

    override fun getList(): List<BagItemData<T>> {
        return bagToolDataList
    }

    override fun getItemIdAt(index: Int): T {
        return bagToolDataList[index].id
    }

    override fun setData(data: BagItemData<T>) {
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
