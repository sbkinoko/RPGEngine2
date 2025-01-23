package gamescreen.menushop.repository.shopmenu

import gamescreen.menushop.domain.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopMenuRepositoryImpl : ShopMenuRepository {
    private val mutableShopItemListStateFlow =
        MutableStateFlow<List<ShopItem>>(
            emptyList()
        )

    override val shopItemListStateFlow: StateFlow<List<ShopItem>> =
        mutableShopItemListStateFlow.asStateFlow()

    private val mutableIsVisibleStateFlow =
        MutableStateFlow(false)

    override val isVisibleStateFlow: StateFlow<Boolean> =
        mutableIsVisibleStateFlow.asStateFlow()

    override fun setList(list: List<ShopItem>) {
        mutableShopItemListStateFlow.value = list

        mutableIsVisibleStateFlow.value = list.isNotEmpty()
    }
}
