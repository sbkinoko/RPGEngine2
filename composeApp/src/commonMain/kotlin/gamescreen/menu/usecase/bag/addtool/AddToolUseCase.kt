package gamescreen.menu.usecase.bag.addtool

import data.item.ItemId

// fixme itemに変える
interface AddToolUseCase<T : ItemId> {
    operator fun invoke(
        toolId: T,
        toolNum: Int,
    )
}
