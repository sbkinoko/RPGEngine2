package gamescreen.menu.item.tool.target

import core.domain.AbleType
import core.domain.item.Tool
import data.repository.item.tool.ToolId
import data.repository.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.target.UsableTargetViewModel
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import gamescreen.menu.usecase.usetoolinmap.UseItemInMapUseCase
import org.koin.core.component.inject

class ToolTargetViewModel(
    useItemInMapUseCase: UseItemInMapUseCase,
) : UsableTargetViewModel<ToolId, Tool>(useItemInMapUseCase) {
    override val itemRepository: ToolRepository by inject()
    private val indexRepository: IndexRepository by inject()

    private val getToolIdUseCase: GetToolIdUseCase by inject()

    override val boundedMenuType: MenuType
        get() = MenuType.TOOL_TARGET

    override val itemId: ToolId
        get() = getToolIdUseCase.invoke(
            userId = userRepository.userId,
            index = indexRepository.index,
        )

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }
}
