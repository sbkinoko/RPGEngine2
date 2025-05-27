package gamescreen.menu.repository.menustate

import gamescreen.menu.domain.MenuType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuStateRepositoryImpl : MenuStateRepository {
    private val mutableCommandStateFlow = MutableStateFlow(MenuType.Main)
    override val menuTypeStateFlow: StateFlow<MenuType>
        get() = mutableCommandStateFlow.asStateFlow()

    override val nowMenuType: MenuType
        get() = mutableList.last()

    private val mutableList: MutableList<MenuType> = mutableListOf(
        MenuType.Main,
    )

    override fun push(menuType: MenuType) {
        mutableList.add(menuType)
        emit()
    }

    override fun pop() {
        // 空になることはない
        if (mutableList.size == 1) {
            return
        }
        mutableList.removeAt(mutableList.size - 1)
        emit()
    }

    private fun emit() {
        mutableCommandStateFlow.value = nowMenuType
    }

    override fun reset() {
        mutableList.clear()
        push(MenuStateRepository.INITIAL_MENU_TYPE)
    }
}
