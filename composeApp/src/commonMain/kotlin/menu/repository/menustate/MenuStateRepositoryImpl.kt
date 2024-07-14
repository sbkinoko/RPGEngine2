package menu.repository.menustate

import kotlinx.coroutines.flow.Flow
import menu.domain.MenuType

class MenuStateRepositoryImpl : MenuStateRepository {
    override val menuTypeFlow: Flow<MenuType>
        get() = TODO("Not yet implemented")

    override var menuType: MenuType
        get() = TODO("Not yet implemented")
        set(value) {}
}
