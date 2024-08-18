package battle.command.main

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import menu.domain.SelectManager

class MainViewModel : BattleChildViewModel() {
    override val boundCommand: CommandType = MainCommand

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    override fun goNextImpl() {
        when (selectManager.selected) {
            0 -> commandStateRepository.push(
                PlayerActionCommand(
                    playerId = 0,
                )
            )
        }
    }
}
