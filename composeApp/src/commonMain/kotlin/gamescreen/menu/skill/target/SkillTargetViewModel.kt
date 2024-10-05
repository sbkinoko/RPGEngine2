package menu.skill.target

import common.values.playerNum
import core.confim.repository.ConfirmRepository
import core.domain.AbleType
import core.domain.Place
import core.domain.item.skill.HealSkill
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.text.repository.TextRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.target.TargetRepository
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.usecase.useskill.UseSkillUseCase
import org.koin.core.component.inject

class SkillTargetViewModel : MenuChildViewModel() {
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()
    private val targetRepository: TargetRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val playerRepository: PlayerRepository by inject()

    private val confirmRepository: ConfirmRepository by inject()
    private val textRepository: TextRepository by inject()

    private val useSkillUseCase: UseSkillUseCase by inject()
    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    override val canBack: Boolean
        get() = true

    val user: Int
        get() = skillUserRepository.skillUserId

    val skillId: Int
        get() = useSkillIdRepository.skillId

    val explain: String
        get() = skillRepository.getSkill(skillId).explain

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

    override fun selectable(): Boolean {
        return canSelect(selectManager.selected)
    }

    fun canSelect(target: Int): Boolean {
        val targetStatus = playerRepository.getStatus(id = target)
        val skill = skillRepository.getSkill(skillId)

        if (skill !is HealSkill) {
            // 回復じゃなかったら使えないはず
            return false
        }

        val targetType = skill.targetType

        if (targetType.canSelect(targetStatus).not()) {
            // 対象にとれなかった
            return false
        }

        val userStatus = playerRepository.getStatus(id = user)

        val ableType = checkCanUseSkillUseCase.invoke(
            status = userStatus,
            skillId = skillId,
            here = Place.MAP,
        )

        return ableType == AbleType.Able
    }

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

    fun backWindow() {
        commandRepository.pop()
    }
}
