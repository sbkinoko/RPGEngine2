package menu.repository.menustate

import kotlinx.coroutines.flow.Flow
import menu.domain.MenuType

interface MenuStateRepository {
    val screenTypeFlow: Flow<MenuType>

    var screenType: MenuType

    companion object {
        val INITIAL_SCREEN_TYPE: MenuType = MenuType.None
    }
}
