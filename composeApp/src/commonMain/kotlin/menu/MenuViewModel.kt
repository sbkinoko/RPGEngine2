package menu

import controller.domain.ControllerCallback
import controller.domain.Stick
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import menu.usecase.getviewmodelbycommandtype.GetControllerByCommandTypeUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()

    private val getControllerByCommandTypeUseCase: GetControllerByCommandTypeUseCase by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.commandTypeFlow

    private val childController: ControllerCallback?
        get() = getControllerByCommandTypeUseCase.invoke()

    override fun moveStick(stick: Stick) {
        childController?.moveStick(stick)
    }

    override fun pressA() {
        childController?.pressA()
    }

    override fun pressB() {
        childController?.pressB()
    }

    override fun pressM() {
        childController?.pressM()
    }
}
