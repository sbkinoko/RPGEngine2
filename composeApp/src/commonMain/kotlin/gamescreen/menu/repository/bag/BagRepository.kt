package gamescreen.menu.repository.bag

import gamescreen.menu.domain.BagToolData

interface BagRepository {
    fun add(
        id: Int,
        num: Int,
    )

    fun dec(
        id: Int,
        num: Int,
    )

    fun getList(): List<BagToolData>
}
