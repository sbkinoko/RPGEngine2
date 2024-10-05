package gamescreen.menu.item.tool.user

import core.repository.item.tool.ToolRepository
import gamescreen.menu.Qualifier
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.user.ItemUserViewModel
import gamescreen.menu.item.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ToolUserViewModel : ItemUserViewModel(),
    KoinComponent {
    override val itemRepository: ToolRepository
            by inject()
    override val userRepository: UserRepository
            by inject(qualifier = named(Qualifier.TOOL_USER))
    override val boundedScreenType: MenuType
        get() = MenuType.TOOL_USER
    override val nextScreenType: MenuType
        get() = TODO("Not yet implemented")

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).toolList
    }
}
