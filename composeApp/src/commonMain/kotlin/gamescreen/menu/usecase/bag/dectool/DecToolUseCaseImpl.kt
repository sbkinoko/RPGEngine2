package gamescreen.menu.usecase.bag.dectool

import data.item.tool.ToolId
import gamescreen.menu.repository.bag.BagRepository

class DecToolUseCaseImpl(
    private val bagRepository: BagRepository,
) : DecToolUseCase {
    override fun invoke(
        itemId: ToolId,
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
