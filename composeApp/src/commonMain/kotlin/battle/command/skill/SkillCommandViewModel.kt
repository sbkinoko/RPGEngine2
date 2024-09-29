package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.BattleCommandType
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import core.domain.Const
import core.repository.player.PlayerRepository
import core.repository.skill.SkillRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()

    val skillList: List<Int>
        get() {
            return playerRepository.getStatus(playerId).skillList
        }

    val playerId: Int
        get() = (commandRepository.nowCommandType as? SkillCommand)?.playerId
            ?: Const.INITIAL_PLAYER


    private val selectedSkillId: Int
        get() = skillList[selectManager.selected]

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        selectManager.selected = actionRepository.getAction(
            playerId = playerId
        ).skillId ?: 0
    }

    override fun selectable(): Boolean {
        return canUse(selectedSkillId)
    }

    fun getName(id: Int): String {
        return skillRepository.getSkill(id).name
    }

    fun canUse(id: Int): Boolean {
        // todo checkCanUseSkillUseCase使う
        return skillRepository.getSkill(id).canUse(
            playerRepository.getStatus(playerId).mp.value
        )
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is SkillCommand
    }

    override fun goNextImpl() {
        val skillId = selectedSkillId
        //　使えないので進まない
        if (canUse(skillId).not()) {
            return
        }

        actionRepository.setAction(
            actionType = ActionType.Skill,
            playerId = playerId,
            skillId = skillId,
        )
        when (skillRepository.getSkill(skillId)) {
            is battle.domain.AttackSkill -> {
                commandRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            is battle.domain.HealSkill -> {
                commandRepository.push(
                    SelectAllyCommand(playerId),
                )
            }
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = skillList.size,
    )
}
