package gamescreen.menu.repository.bag

import gamescreen.menu.domain.BagToolData

class BagRepositoryImpl : BagRepository {
    private var bagToolDataList: List<BagToolData> = listOf()

    override fun getList(): List<BagToolData> {
        return bagToolDataList
    }

    override fun getItemIdAt(index: Int): Int {
        return bagToolDataList[index].id
    }

    override fun setData(data: BagToolData) {
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
