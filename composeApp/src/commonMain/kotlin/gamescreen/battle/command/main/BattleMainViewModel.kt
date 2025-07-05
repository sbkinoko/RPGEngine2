package gamescreen.battle.command.main

import core.menu.SelectCore
import core.menu.SelectCoreEnum
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.MainCommand
import gamescreen.battle.domain.PlayerActionCommand

class BattleMainViewModel :
    BattleChildViewModel<MainCommandType>() {

    override var selectCore: SelectCore<MainCommandType> = SelectCoreEnum(
        entries = MainCommandType.entries,
    )

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType is MainCommand
    }

    override fun goNextImpl() {
        when (selectCore.stateFlow.value) {
            MainCommandType.Battle -> commandRepository.push(
                PlayerActionCommand(
                    playerId = 0,
                )
            )

            MainCommandType.Escape -> commandRepository.push(
                EscapeCommand
            )
        }
    }
}
