package menu

import controller.domain.ControllerCallback
import kotlinx.coroutines.flow.SharedFlow
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.menuTypeFlow
    override fun moveStick(dx: Float, dy: Float) {

    }

    override var pressA: () -> Unit = {}
    override var pressB: () -> Unit = {}
    override var pressM: () -> Unit = {
        screenTypeRepository.screenType = ScreenType.FIELD
    }
}
