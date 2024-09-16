package menu

import main.menu.SelectableChildViewModel
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.inject

abstract class MenuChildViewModel : SelectableChildViewModel<MenuType>() {
    override val commandRepository: MenuStateRepository by inject()
}
