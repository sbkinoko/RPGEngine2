package gamescreen.menu.usecase.bag.dectool

import core.repository.bag.BagRepository
import data.repository.monster.item.ItemId

class DecToolUseCaseImpl<T : ItemId>(
    private val bagRepository: BagRepository<T>,
) : DecItemUseCase<T> {
    override fun invoke(
        itemId: T,
        itemNum: Int,
    ) {
        val data = bagRepository.getList().find {
            it.id == itemId
        }

        if (data == null) {
            throw IllegalStateException()
        }

        if (data.num - itemNum < 0) {
            throw IllegalStateException()
        }

        bagRepository.setData(
            data = data.copy(
                num = data.num - itemNum,
            )
        )
    }
}
