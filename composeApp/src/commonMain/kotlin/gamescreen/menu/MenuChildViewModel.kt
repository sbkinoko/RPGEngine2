package gamescreen.menu

import core.menu.SelectableChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.usecase.backfield.CloseMenuUseCase
import org.koin.core.component.inject

abstract class MenuChildViewModel : SelectableChildViewModel<MenuType>() {
    override val commandRepository: MenuStateRepository by inject()
    private val closeMenuUseCase: CloseMenuUseCase by inject()

    override fun pressM() {
        closeMenuUseCase.invoke()
    }
}
