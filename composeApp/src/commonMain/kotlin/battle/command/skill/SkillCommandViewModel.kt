package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.CommandType
import battle.domain.SelectAllyCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import battle.repository.skill.ATTACK_TO_2
import battle.repository.skill.CANT_USE
import battle.repository.skill.HEAL_SKILL
import battle.repository.skill.REVIVE_SKILL
import battle.repository.skill.SkillRepository
import common.repository.player.PlayerRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()

    val skillList = listOf(
        ATTACK_TO_2,
        CANT_USE,
        HEAL_SKILL,
        REVIVE_SKILL,
    )

    val playerId: Int
        get() = (commandStateRepository.nowCommandType as SkillCommand).playerId

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
        return skillRepository.getSkill(id).canUse(
            playerRepository.getStatus(playerId).mp.value
        )
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
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
                commandStateRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            is battle.domain.HealSkill -> {
                commandStateRepository.push(
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
