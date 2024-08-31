package battle.command.playeraction

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import battle.domain.SkillCommand
import menu.domain.SelectManager

class PlayerActionViewModel : BattleChildViewModel() {
    val normalAttack = 0
    val skill = 1

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        val playerId = (commandStateRepository.nowCommandType as PlayerActionCommand).playerId

        when (selectManager.selected) {
            normalAttack -> commandStateRepository.push(
                SelectEnemyCommand(playerId),
            )

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
