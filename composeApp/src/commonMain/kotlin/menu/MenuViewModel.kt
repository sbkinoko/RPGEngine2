package menu

import controller.domain.ControllerCallback
import controller.domain.StickPosition
import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.text.TextViewModel
import core.text.repository.TextRepository
import kotlinx.coroutines.flow.SharedFlow
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.skill.list.SkillListViewModel
import menu.skill.target.SkillTargetViewModel
import menu.skill.user.SkillUserViewModel
import menu.status.StatusViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MenuViewModel : KoinComponent, ControllerCallback {
    private val menuStateRepository: MenuStateRepository by inject()
    private val confirmRepository: ConfirmRepository by inject()
    private val textRepository: TextRepository by inject()

    val menuType: SharedFlow<MenuType> = menuStateRepository.commandTypeFlow

    private val mainMenuViewModel: MainMenuViewModel by inject()
    private val statusViewModel: StatusViewModel by inject()
    private val skillUserViewModel: SkillUserViewModel by inject()
    private val skillListViewModel: SkillListViewModel by inject()
    private val skillTargetViewModel: SkillTargetViewModel by inject()

    private val confirmViewModel: ConfirmViewModel by inject()
    private val textViewModel: TextViewModel by inject()

    override fun moveStick(stickPosition: StickPosition) {
        menuStateRepository.nowCommandType
            .toViewModel()?.moveStick(
                stickPosition
            )
    }

    private fun MenuType.toViewModel(): ControllerCallback? {
        if (confirmRepository.nowCommandType) {
            return confirmViewModel
        }

        if (textRepository.nowCommandType) {
            return textViewModel
        }

        return when (this) {
            MenuType.Main -> mainMenuViewModel
            MenuType.Status -> statusViewModel
            MenuType.SKILL_USER -> skillUserViewModel
            MenuType.SKILL_LST -> skillListViewModel
            MenuType.SKILL_TARGET -> skillTargetViewModel
            MenuType.Item3 -> null
            MenuType.Item4 -> null
            MenuType.Item5 -> null
            MenuType.Item6 -> null
        }
    }

    override fun pressA() {
        menuStateRepository.nowCommandType.toViewModel()?.pressA()
    }

    override fun pressB() {
        menuStateRepository.nowCommandType.toViewModel()?.pressB()
    }

    override fun pressM() {
        menuStateRepository.nowCommandType.toViewModel()?.pressM()
    }
}
