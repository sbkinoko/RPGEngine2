package gamescreen.battle

import core.menu.SelectableChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.repository.commandstate.CommandStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BattleChildViewModel :
    SelectableChildViewModel<BattleCommandType>(),
    KoinComponent {
    override val commandRepository: CommandStateRepository
            by inject()
}
