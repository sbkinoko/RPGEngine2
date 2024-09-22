package menu.skill.target

import common.values.playerNum
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.repository.skilluser.SkillUserRepository
import org.koin.core.component.inject

class SkillTargetViewModel : MenuChildViewModel() {
    private val skillUserRepository: SkillUserRepository by inject()

    override val canBack: Boolean
        get() = true

    val user: Int
        get() = skillUserRepository.skillUserId

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.SKILL_TARGET
    }

    override fun goNextImpl() {
        TODO("Not yet implemented")
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )
}
