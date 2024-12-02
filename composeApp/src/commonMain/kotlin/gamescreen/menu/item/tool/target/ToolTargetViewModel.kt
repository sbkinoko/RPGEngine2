package gamescreen.menu.item.tool.target

import core.domain.AbleType
import core.repository.item.tool.ToolRepository
import core.usecase.item.usetool.UseToolUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.ItemTargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.text.TextBoxData
import org.koin.core.component.inject

class ToolTargetViewModel : ItemTargetViewModel() {
    override val itemRepository: ToolRepository by inject()
    private val indexRepository: IndexRepository by inject()

    private val useToolUseCase: UseToolUseCase by inject()
    private val getToolIdUseCase: GetToolIdUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.TOOL_TARGET

    override val itemId: Int
        get() = getToolIdUseCase.invoke(
            userId = userRepository.userId,
            index = indexRepository.index,
        )

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun selectYes() {
        // textを表示
        textRepository.push(
            TextBoxData(
                text = "回復しました",
                callBack = { commandRepository.pop() }
            )
        )

        useToolUseCase.invoke(
            userId = userRepository.userId,
            index = indexRepository.index,
            targetId = targetRepository.target,
        )
    }
}
