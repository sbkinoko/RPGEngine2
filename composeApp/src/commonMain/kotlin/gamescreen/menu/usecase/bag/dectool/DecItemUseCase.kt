package gamescreen.menu.usecase.bag.dectool

import data.repository.item.ItemId

interface DecItemUseCase<T : ItemId> {
    operator fun invoke(
        itemId: T,
        itemNum: Int,
    )
}
