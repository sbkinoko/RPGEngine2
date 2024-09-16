package menu

import controller.domain.ControllerCallback
import controller.domain.StickPosition
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.skill.list.SkillListViewModel
import menu.skill.user.SkillUserViewModel
import menu.status.StatusViewModel
import menu.usecase.backfield.BackFieldUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()

    private val backFieldUseCase: BackFieldUseCase by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.menuTypeFlow

    private val mainMenuViewModel: MainMenuViewModel by inject()
    private val statusViewModel: StatusViewModel by inject()
    private val skillUserViewModel: SkillUserViewModel by inject()
    private val skillListViewModel: SkillListViewModel by inject()

    override fun moveStick(stickPosition: StickPosition) {
        menuStateRepository.menuType
            .toViewModel()?.moveStick(
                stickPosition
            )
    }

    private fun MenuType.toViewModel(): ControllerCallback? {
        return when (this) {
            MenuType.Main -> mainMenuViewModel
            MenuType.Status -> statusViewModel
            MenuType.SKILL_USER -> skillUserViewModel
            MenuType.SKILL_LST -> skillListViewModel
            MenuType.Item3 -> null
            MenuType.Item4 -> null
            MenuType.Item5 -> null
            MenuType.Item6 -> null
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
