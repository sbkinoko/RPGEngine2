package menu.repository.menustate

import kotlinx.coroutines.flow.Flow
import menu.domain.MenuType

interface MenuStateRepository {
    val menuTypeFlow: Flow<MenuType>

    var menuType: MenuType

    companion object {
        val INITIAL_MENU_TYPE: MenuType = MenuType.None
    }
}
