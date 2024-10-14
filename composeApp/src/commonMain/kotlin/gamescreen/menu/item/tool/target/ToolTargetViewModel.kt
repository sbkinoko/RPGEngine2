package gamescreen.menu.item.tool.target

import core.domain.AbleType
import core.repository.item.tool.ToolRepository
import core.text.repository.TextRepository
import core.usecase.item.usetool.UseToolUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.ItemTargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import org.koin.core.component.inject

class ToolTargetViewModel : ItemTargetViewModel() {
    override val itemRepository: ToolRepository by inject()
    private val textRepository: TextRepository by inject()
    private val indexRepository: IndexRepository by inject()

    private val useToolUseCase: UseToolUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }


    override fun selectYes() {
        // textを表示
        textRepository.push(true)
        textRepository.setText("回復しました")

        useToolUseCase.invoke(
            userId = userRepository.userId,
            toolId = useItemIdRepository.itemId,
            index = indexRepository.index,
            targetId = targetRepository.target,
        )
    }
}
