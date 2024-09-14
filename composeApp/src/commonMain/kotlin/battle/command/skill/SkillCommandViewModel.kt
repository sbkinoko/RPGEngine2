package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.CommandType
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import battle.repository.skill.HEAL_SKILL
import battle.repository.skill.SkillRepository
import common.repository.player.PlayerRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()
    private val playerRepository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()

    val skillList = listOf(
        0,
        1,
        HEAL_SKILL,
    )

    val playerId: Int
        get() = (commandStateRepository.nowCommandType as SkillCommand).playerId

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        selectManager.selected = actionRepository.getAction(
            playerId = playerId
        ).skillId ?: 0
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
        //　使えないので進まない
        if (canUse(selectManager.selected).not()) {
            return
        }

        actionRepository.setAction(
            actionType = ActionType.Skill,
            playerId = playerId,
            skillId = selectManager.selected,
        )


        commandStateRepository.push(
            SelectEnemyCommand(playerId),
        )
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = skillList.size,
    )
}
