package gamescreen.menushop.usecase.setshopitem

import data.item.tool.ToolId
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import values.event.ShopId

class SetShopItemUseCaseImpl(
    private val shopMenuRepository: ShopMenuRepository,
) : SetShopItemUseCase {
    override fun invoke(shopId: ShopId) {
        val list = when (shopId) {
            ShopId.Type1 -> {
                List(2) {
                    ShopItem(
                        name = "アイテム${it + 1}",
                        price = (it + 1) * (it + 1) * 100,
                        explain = "アイテム${it + 1}の説明",
                        // fixme 正しいidを入れる
                        itemId = ToolId.HEAL1,
                    )
                }
            }

            ShopId.Type2 -> {
                List(3) {
                    ShopItem(
                        name = "アイテム${it + 1}",
                        price = (it + 1) * (it + 1) * 100,
                        explain = "アイテム${it + 1}の説明",
                        // fixme 正しいidを入れる
                        itemId = ToolId.HEAL1,
                    )
                }
            }
        }

        shopMenuRepository.setList(
            list = list,
        )
        shopMenuRepository.setShopType(ShopType.BUY)
    }
}
