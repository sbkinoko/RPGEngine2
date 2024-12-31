package gamescreen.menu.repository.menustate

import gamescreen.menu.domain.MenuType
import kotlinx.coroutines.flow.StateFlow

interface MenuStateRepository {
    //　mを押して画面を戻った時に状態を初期化する
    fun reset()

    val menuTypeStateFlow: StateFlow<MenuType>
    val nowMenuType: MenuType

    fun push(menuType: MenuType)

    fun pop()

    companion object {
        val INITIAL_MENU_TYPE: MenuType = MenuType.Main
    }
}
