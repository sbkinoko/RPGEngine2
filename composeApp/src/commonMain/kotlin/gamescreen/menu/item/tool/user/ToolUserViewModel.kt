package gamescreen.menu.item.tool.user

import core.repository.item.tool.ToolRepository
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.user.ItemUserViewModel
import gamescreen.menu.item.repository.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToolUserViewModel : ItemUserViewModel(),
    KoinComponent {
    override val itemRepository: ToolRepository
            by inject()
    override val userRepository: UserRepository
            by inject()
    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_USER
    override val nextScreenType: MenuType
        get() = MenuType.TOOL_LIST

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).toolList
    }
}
