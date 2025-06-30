package gamescreen.battle.command.escape

import core.domain.BattleResult
import core.menu.SelectCore
import core.menu.SelectCoreEnum
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.command.escape.EscapeCommandType.BackToBattle
import gamescreen.battle.command.escape.EscapeCommandType.Escape
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.FinishCommand

class EscapeViewModel
    : BattleChildViewModel<EscapeCommandType>() {
    override var selectCore: SelectCore<EscapeCommandType> = SelectCoreEnum(
        entries = EscapeCommandType.entries,
    )

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType == EscapeCommand
    }

    override fun goNextImpl() {
        when (selectedFlowState.value) {
            Escape -> this.commandRepository.push(
                FinishCommand(
                    battleResult = BattleResult.Escape,
                )
            )

            BackToBattle -> commandRepository.pop()
        }
    }
}
