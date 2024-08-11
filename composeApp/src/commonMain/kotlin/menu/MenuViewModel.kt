package menu

import controller.domain.ControllerCallback
import kotlinx.coroutines.flow.SharedFlow
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.menuTypeFlow

    val mainMenuViewModel: MainMenuViewModel = MainMenuViewModel()

    override fun moveStick(dx: Float, dy: Float) {

    }

    fun setMenuType(menuType: MenuType) {
        menuStateRepository.menuType = menuType
    }

    override var pressA: () -> Unit = {}
    override var pressB: () -> Unit = {
        if (menuStateRepository.menuType == MenuType.Main) {
            backToField()
        } else {
            menuStateRepository.pop()
        }
    }

    override var pressM: () -> Unit = {
        backToField()
    }

    private fun backToField() {
        screenTypeRepository.screenType = ScreenType.FIELD
        menuStateRepository.reset()
    }
}
