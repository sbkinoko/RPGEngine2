package gamescreen.menushop.usecase.setshopitem

import values.event.ShopId

interface SetShopItemUseCase {
    operator fun invoke(
        shopId: ShopId,
    )
}
