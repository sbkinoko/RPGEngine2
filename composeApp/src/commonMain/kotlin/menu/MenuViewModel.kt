package menu

import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.status.StatusViewModel
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()

    private val backFieldUseCase: BackFieldUseCase by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.menuTypeFlow

    val mainMenuViewModel: MainMenuViewModel = MainMenuViewModel()
    val statusViewModel: StatusViewModel = StatusViewModel()

    override fun moveStick(stickPosition: StickPosition) {
        menuStateRepository.menuType
            .toViewModel()?.moveStick(
                stickPosition
            )
    }

    fun setMenuType(menuType: MenuType) {
        menuStateRepository.menuType = menuType
    }

    private fun MenuType.toViewModel(): ControllerCallback? {
        return when (this) {
            MenuType.Main -> mainMenuViewModel
            MenuType.Status -> statusViewModel
            else -> null
        }
    }

    override fun pressA() {
        menuStateRepository.menuType.toViewModel()?.pressA()
    }

    override fun pressB() {
        menuStateRepository.menuType.toViewModel()?.pressB()
    }

    override fun pressM() {
        backFieldUseCase()
    }
}
