package menu

import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.SharedFlow
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.status.StatusViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.menuTypeFlow

    val mainMenuViewModel: MainMenuViewModel = MainMenuViewModel()
    val statusViewModel: StatusViewModel = StatusViewModel()

    override fun moveStick(stickPosition: StickPosition) {
        when (menuStateRepository.menuType) {
            MenuType.Main -> {
                mainMenuViewModel.moveStick(
                    stickPosition = stickPosition,
                )
            }
            MenuType.Status -> {
                statusViewModel.moveStick(
                    stickPosition = stickPosition,
                )
            }

            else -> Unit
        }
    }

    fun setMenuType(menuType: MenuType) {
        menuStateRepository.menuType = menuType
    }

    override var pressA: () -> Unit = {
        when (menuStateRepository.menuType) {
            MenuType.Main -> {
                mainMenuViewModel.pressA()
            }

            else -> Unit
        }
    }
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
