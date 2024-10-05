package gamescreen.battle.command.main

import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand
import gamescreen.menu.domain.SelectManager

class BattleMainViewModel : BattleChildViewModel() {
    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is MainCommand
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )

    override fun goNextImpl() {
        when (selectManager.selected) {
            0 -> commandRepository.push(
                PlayerActionCommand(
                    playerId = 0,
                )
            )

            1 -> commandRepository.push(
                EscapeCommand
            )
        }
    }
}
