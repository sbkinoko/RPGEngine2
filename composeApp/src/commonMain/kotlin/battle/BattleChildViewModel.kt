package battle

import battle.domain.BattleCommandType
import battle.repository.commandstate.CommandStateRepository
import main.menu.SelectableChildViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BattleChildViewModel :
    SelectableChildViewModel<BattleCommandType>(),
    KoinComponent {
    override val commandRepository: CommandStateRepository
            by inject()
}
