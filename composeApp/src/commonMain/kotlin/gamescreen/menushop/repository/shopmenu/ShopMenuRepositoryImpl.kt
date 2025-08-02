package gamescreen.menushop.repository.shopmenu

import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.ShopType
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

    private val mutableShopTypeStateFlow =
        MutableStateFlow(ShopType.CLOSE)

    override val shopTypeStateFlow: StateFlow<ShopType> =
        mutableShopTypeStateFlow.asStateFlow()

    override fun setList(list: List<ShopItem>) {
        mutableShopItemListStateFlow.value = list
    }

    override fun setShopType(shopType: ShopType) {
        mutableShopTypeStateFlow.value = shopType
    }
}
