package gamescreen.menu.usecase.bag.addtool

import data.item.ItemId

interface AddToolUseCase<T : ItemId> {
    operator fun invoke(
        toolId: T,
        toolNum: Int,
    )
}
