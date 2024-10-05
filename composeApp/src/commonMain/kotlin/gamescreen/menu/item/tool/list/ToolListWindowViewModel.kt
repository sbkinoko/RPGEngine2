package gamescreen.menu.item.tool.list

import core.repository.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.itemselect.ItemListViewModel
import org.koin.core.component.inject

class ToolListWindowViewModel : ItemListViewModel() {
    override val itemRepository: ToolRepository
            by inject()
    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_LIST
    override val itemList: List<Int>
        get() = playerRepository.getStatus(userId).toolList

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).toolList
    }
}
