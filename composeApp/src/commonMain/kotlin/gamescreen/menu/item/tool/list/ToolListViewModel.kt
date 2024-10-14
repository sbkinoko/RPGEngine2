package gamescreen.menu.item.tool.list

import core.domain.AbleType
import core.repository.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import org.koin.core.component.inject

class ToolListViewModel : ItemListViewModel() {
    override val itemRepository: ToolRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_LIST
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_TARGET

    override fun getAbleType(): AbleType {
        return AbleType.Able
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).toolList
    }
}
