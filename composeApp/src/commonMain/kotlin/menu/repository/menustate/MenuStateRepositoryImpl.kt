package menu.repository.menustate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import menu.domain.MenuType

class MenuStateRepositoryImpl : MenuStateRepository {
    override val menuTypeFlow: MutableSharedFlow<MenuType> = MutableSharedFlow(replay = 1)

    private var _menuType: MenuType = MenuStateRepository.INITIAL_MENU_TYPE

    override var menuType: MenuType
        get() = _menuType
        set(value) {
            _menuType = value
            CoroutineScope(Dispatchers.Default).launch {
                menuTypeFlow.emit(value)
            }
        }
}
