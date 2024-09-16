package battle.command.main

import battle.BattleChildViewModel
import battle.domain.BattleCommandType
import battle.domain.EscapeCommand
import battle.domain.MainCommand
import battle.domain.PlayerActionCommand
import menu.domain.SelectManager

class BattleMainViewModel : BattleChildViewModel() {
    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(battleCommandType: BattleCommandType): Boolean {
        return battleCommandType is MainCommand
    }

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
            1 -> commandStateRepository.push(
                EscapeCommand
            )
        }
    }
}
