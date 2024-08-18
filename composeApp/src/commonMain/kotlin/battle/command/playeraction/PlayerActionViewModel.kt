package battle.command.playeraction

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.PlayerActionCommand
import battle.domain.SelectEnemyCommand
import menu.domain.SelectManager

class PlayerActionViewModel : BattleChildViewModel() {
    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is PlayerActionCommand
    }

    override fun goNextImpl() {
        val playerId = (commandStateRepository.nowCommandType as PlayerActionCommand).playerId

        when (selectManager.selected) {
            0 -> commandStateRepository.push(
                SelectEnemyCommand(playerId)
            )

            else -> Unit
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
