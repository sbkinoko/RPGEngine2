package menu.usecase.getviewmodelbycommandtype

import controller.domain.ControllerCallback
import core.confim.ConfirmViewModel
import core.confim.repository.ConfirmRepository
import core.text.TextViewModel
import core.text.repository.TextRepository
import menu.domain.MenuType
import menu.main.MainMenuViewModel
import menu.repository.menustate.MenuStateRepository
import menu.skill.list.SkillListViewModel
import menu.skill.target.SkillTargetViewModel
import menu.skill.user.SkillUserViewModel
import menu.status.StatusViewModel

class GetControllerByCommandTypeUseCaseImpl(
    private val confirmRepository: ConfirmRepository,
    private val textRepository: TextRepository,
    private val menuStateRepository: MenuStateRepository,

    private val mainMenuViewModel: MainMenuViewModel,
    private val statusViewModel: StatusViewModel,
    private val skillUserViewModel: SkillUserViewModel,
    private val skillListViewModel: SkillListViewModel,
    private val skillTargetViewModel: SkillTargetViewModel,
    private val confirmViewModel: ConfirmViewModel,
    private val textViewModel: TextViewModel,
) : GetControllerByCommandTypeUseCase {


    override fun invoke(): ControllerCallback? {
        if (confirmRepository.nowCommandType) {
            return confirmViewModel
        }

        if (textRepository.nowCommandType) {
            return textViewModel
        }

        return when (menuStateRepository.nowCommandType) {
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
}