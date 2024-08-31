package battle.command.playeraction

import battle.BattleChildViewModel
import battle.domain.ActionType
import battle.domain.CommandType
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import battle.repository.action.ActionRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class PlayerActionViewModel : BattleChildViewModel() {
    private val actionRepository: ActionRepository by inject()

    val normalAttack = 0
    val skill = 1

    val playerId: Int
        get() = (commandStateRepository.nowCommandType as PlayerActionCommand).playerId

    fun init() {
        selectManager.selected = when (
            actionRepository.getAction(playerId = playerId).thisTurnAction
        ) {
            ActionType.Normal -> normalAttack
            ActionType.Skill -> skill
        }
    }

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        when (selectManager.selected) {
            normalAttack -> {
                // 行動を保存
                actionRepository.setAction(
                    actionType = ActionType.Normal,
                    targetNum = 1,
                    playerId = playerId,
                )

                // 画面変更
                commandStateRepository.push(
                    SelectEnemyCommand(playerId),
                )
            }

            skill -> commandStateRepository.push(
                SkillCommand(playerId),
            )
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
