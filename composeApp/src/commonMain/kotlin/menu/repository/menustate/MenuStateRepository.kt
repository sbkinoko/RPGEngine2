package menu.repository.menustate

import kotlinx.coroutines.flow.MutableSharedFlow
import menu.domain.MenuType

interface MenuStateRepository {
    val menuTypeFlow: MutableSharedFlow<MenuType>

    var menuType: MenuType

    fun pop()

    //　mを押して画面を戻った時に状態を初期化する
//    fun reset()

    companion object {
        val INITIAL_MENU_TYPE: MenuType = MenuType.Main
    }
}
