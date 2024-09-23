package menu

import core.menu.SelectableChildViewModel
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.inject

abstract class MenuChildViewModel : SelectableChildViewModel<MenuType>() {
    override val commandRepository: MenuStateRepository by inject()
    private val backFieldUseCase: BackFieldUseCase by inject()

    override fun pressM() {
        backFieldUseCase.invoke()
    }
}
