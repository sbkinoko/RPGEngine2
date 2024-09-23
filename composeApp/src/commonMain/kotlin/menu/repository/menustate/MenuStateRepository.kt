package menu.repository.menustate

import core.repository.command.CommandRepository
import menu.domain.MenuType

interface MenuStateRepository : CommandRepository<MenuType> {
    //　mを押して画面を戻った時に状態を初期化する
    fun reset()

    companion object {
        val INITIAL_MENU_TYPE: MenuType = MenuType.Main
    }
}
