package gamescreen.menu.usecase.getviewmodelbycommandtype

import controller.domain.ControllerCallback
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.skill.list.SkillListViewModel
import gamescreen.menu.item.skill.target.SkillTargetViewModel
import gamescreen.menu.item.skill.user.SkillUserViewModel
import gamescreen.menu.item.tool.give.ToolGiveUserViewModel
import gamescreen.menu.item.tool.list.ToolListViewModel
import gamescreen.menu.item.tool.target.ToolTargetViewModel
import gamescreen.menu.item.tool.user.ToolUserViewModel
import gamescreen.menu.main.MainMenuViewModel
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.menu.status.StatusViewModel

class GetControllerByCommandTypeUseCaseImpl(
    private val menuStateRepository: MenuStateRepository,

    private val mainMenuViewModel: MainMenuViewModel,
    private val statusViewModel: StatusViewModel,

    private val skillUserViewModel: SkillUserViewModel,
    private val skillListViewModel: SkillListViewModel,
    private val skillTargetViewModel: SkillTargetViewModel,

    private val toolUserViewModel: ToolUserViewModel,
    private val toolListViewModel: ToolListViewModel,
    private val toolTargetViewModel: ToolTargetViewModel,
    private val toolGiveUserViewModel: ToolGiveUserViewModel,
) : GetControllerByCommandTypeUseCase {

    override fun invoke(): ControllerCallback? {
        return when (menuStateRepository.nowMenuType) {
            MenuType.Main -> mainMenuViewModel
            MenuType.Status -> statusViewModel
            MenuType.SKILL_USER -> skillUserViewModel
            MenuType.SKILL_LST -> skillListViewModel
            MenuType.SKILL_TARGET -> skillTargetViewModel
            MenuType.TOOL_USER -> toolUserViewModel
            MenuType.TOOL_LIST -> toolListViewModel
            MenuType.TOOL_TARGET -> toolTargetViewModel
            MenuType.TOOL_GIVE -> toolGiveUserViewModel
            MenuType.Item3 -> null
            MenuType.Collision -> null
            MenuType.Item5 -> null
            MenuType.Item6 -> null
        }
    }
}
