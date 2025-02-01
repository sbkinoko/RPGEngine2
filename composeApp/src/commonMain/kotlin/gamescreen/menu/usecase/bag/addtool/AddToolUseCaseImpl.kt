package gamescreen.menu.usecase.bag.addtool

import data.item.tool.ToolId
import gamescreen.menu.domain.BagToolData
import gamescreen.menu.repository.bag.BagRepository

class AddToolUseCaseImpl(
    private val bagRepository: BagRepository,
) : AddToolUseCase {
    override fun invoke(
        toolId: ToolId,
        toolNum: Int,
    ) {
        val list = bagRepository
            .getList()
        if (list.isEmpty()) {
            // 空なので新規追加
            bagRepository.setData(
                BagToolData(toolId, toolNum)
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
                BagToolData(toolId, toolNum)
            )
        }
    }
}
