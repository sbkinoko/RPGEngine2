package menu.repository.menustate

import kotlinx.coroutines.flow.MutableSharedFlow
import menu.domain.MenuType

interface MenuStateRepository {
    val menuTypeFlow: MutableSharedFlow<MenuType>

    var menuType: MenuType

    companion object {
        val INITIAL_MENU_TYPE: MenuType = MenuType.None
    }
}