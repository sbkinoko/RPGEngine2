package gamescreen.menushop.repository.shopmenu

import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.ShopType
import kotlinx.coroutines.flow.StateFlow

interface ShopMenuRepository {
    val shopItemListStateFlow: StateFlow<List<ShopItem>>

    val shopTypeStateFlow: StateFlow<ShopType>

    fun setList(
        list: List<ShopItem>,
    )
}
