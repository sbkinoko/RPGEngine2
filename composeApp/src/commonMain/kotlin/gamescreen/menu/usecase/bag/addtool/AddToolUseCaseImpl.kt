package gamescreen.menu.usecase.bag.addtool

import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import data.item.tool.ToolId

class AddToolUseCaseImpl<T : ToolId>(
    private val bagRepository: BagRepository<T>,
) : AddToolUseCase<T> {
    override fun invoke(
        toolId: T,
        toolNum: Int,
    ) {
        val list = bagRepository
            .getList()
        if (list.isEmpty()) {
            // 空なので新規追加
            bagRepository.setData(
                BagItemData(toolId, toolNum)
            )
            return
        }

        val data = list.firstOrNull {
            it.id == toolId
        }

        if (data != null) {
            // すでにあれば加算する
            bagRepository.setData(
                data.copy(num = data.num + toolNum)
            )
        } else {
            // なければ新規追加
            bagRepository.setData(
                BagItemData(toolId, toolNum)
            )
        }
    }
}
