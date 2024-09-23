package menu.skill.target

import common.values.playerNum
import core.confim.repository.ConfirmRepository
import core.text.repository.TextRepository
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.target.TargetRepository
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.usecase.getskillexplain.GetSkillExplainUseCase
import menu.skill.usecase.useskill.UseSkillUseCase
import org.koin.core.component.inject

class SkillTargetViewModel : MenuChildViewModel() {
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()
    private val targetRepository: TargetRepository by inject()

    private val confirmRepository: ConfirmRepository by inject()
    private val textRepository: TextRepository by inject()

    private val getSkillExplainUseCase: GetSkillExplainUseCase by inject()
    private val useSkillUseCase: UseSkillUseCase by inject()

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

    /**
     * confirmでYesを選んだ時の処理
     */
    fun selectYes() {
        // textを表示
        textRepository.push(true)
        textRepository.setText("回復しました")

        //　スキル処理実行
        useSkillUseCase.invoke()
    }
}
