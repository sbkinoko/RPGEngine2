package gamescreen.menu.repository.bag

import gamescreen.menu.domain.BagToolData

interface BagRepository {
    fun getList(): List<BagToolData>

    fun getItemIdAt(index: Int): Int

    fun setData(data: BagToolData)
}
