package gamescreen.menushop.repository.shopmenu

import gamescreen.menushop.domain.ShopItem
import kotlinx.coroutines.flow.StateFlow

interface ShopMenuRepository {
    val shopItemListStateFlow: StateFlow<List<ShopItem>>

    val isVisibleStateFlow: StateFlow<Boolean>

    fun setList(
        list: List<ShopItem>,
    )
}
