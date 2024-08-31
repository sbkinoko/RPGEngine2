package battle.command.skill

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.CommandType
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class SkillCommandViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()

    val playerId: Int
        get() = (commandStateRepository.nowCommandType as SkillCommand).playerId

    fun init() {
        // 最後に選ばれていたスキルを呼び出し
        selectManager.selected = actionRepository.getAction(
            playerId = playerId
        ).skillId ?: 0
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is SkillCommand
    }

    override fun goNextImpl() {
        actionRepository.setAction(
            actionType = ActionType.Skill,
            playerId = playerId,
            skillId = selectManager.selected,
            targetNum = 2,
        )

        commandStateRepository.push(
            SelectEnemyCommand(playerId),
        )
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
