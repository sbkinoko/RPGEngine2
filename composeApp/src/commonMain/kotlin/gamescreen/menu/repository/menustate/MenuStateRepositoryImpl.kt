package gamescreen.menu.repository.menustate

import gamescreen.menu.domain.MenuType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuStateRepositoryImpl : MenuStateRepository {
    override val commandStateFlow: StateFlow<MenuType>
        get() = TODO("Not yet implemented")

    override val nowCommandType: MenuType
        get() = mutableList.last()

    override val commandTypeFlow: MutableSharedFlow<MenuType> = MutableSharedFlow(replay = 1)

    private val mutableList: MutableList<MenuType> = mutableListOf(
        MenuType.Main,
    )

    override fun push(commandType: MenuType) {
        mutableList.add(commandType)
        emit()
    }

    override fun pop() {
        // 空になることはない
        if (mutableList.size == 1) {
            return
        }
        mutableList.removeLast()
        emit()
    }

    private fun emit() {
        CoroutineScope(Dispatchers.Default).launch {
            this@MenuStateRepositoryImpl.commandTypeFlow.emit(nowCommandType)
        }
    }

    override fun reset() {
        while (mutableList.size != 0) {
            mutableList.removeLast()
        }
        push(MenuStateRepository.INITIAL_MENU_TYPE)
    }
}
