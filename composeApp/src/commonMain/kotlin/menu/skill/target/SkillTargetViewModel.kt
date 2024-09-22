package menu.skill.target

import common.values.playerNum
import core.confim.repository.ConfirmRepository
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.target.TargetRepository
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.usecase.GetSkillExplainUseCase
import org.koin.core.component.inject

class SkillTargetViewModel : MenuChildViewModel() {
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()
    private val targetRepository: TargetRepository by inject()

    private val confirmRepository: ConfirmRepository by inject()

    private val getSkillExplainUseCase: GetSkillExplainUseCase by inject()

    override val canBack: Boolean
        get() = true

    val user: Int
        get() = skillUserRepository.skillUserId

    val id: Int
        get() = useSkillIdRepository.skillId

    val explain: String
        get() = getSkillExplainUseCase.invoke(id)

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.SKILL_TARGET
    }

    override fun goNextImpl() {
        targetRepository.target = selectManager.selected
        confirmRepository.push(true)
    }

    override var selectManager: SelectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )
}
