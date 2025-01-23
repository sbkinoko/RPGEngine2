package gamescreen.menushop.usecase.setshopitem

import gamescreen.map.data.ShopId

interface SetShopItemUseCase {
    operator fun invoke(
        shopId: ShopId,
    )
}
