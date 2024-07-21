package menu.repository.menustate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import menu.domain.MenuType

class MenuStateRepositoryImpl : MenuStateRepository {
    override val menuTypeFlow: MutableSharedFlow<MenuType> = MutableSharedFlow(replay = 1)

    private var _menuType: MenuType = MenuStateRepository.INITIAL_MENU_TYPE

    private val mutableList: MutableList<MenuType> = mutableListOf(
        MenuType.Main,
    )

    override fun pop() {
        // 空になることはない
        if (mutableList.size == 1) {
            return
        }
        mutableList.removeLast()
        menuType = mutableList.last()
    }

    override var menuType: MenuType
        get() = _menuType
        set(value) {
            _menuType = value
            mutableList.add(value)
            CoroutineScope(Dispatchers.Default).launch {
                menuTypeFlow.emit(value)
            }
        }
}
