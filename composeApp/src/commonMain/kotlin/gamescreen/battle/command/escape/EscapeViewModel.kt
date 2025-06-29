package gamescreen.battle.command.escape

import core.domain.BattleResult
import core.repository.screentype.ScreenTypeRepository
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
import gamescreen.battle.domain.FinishCommand
import gamescreen.menu.domain.SelectManager
import org.koin.core.component.inject

class EscapeViewModel : BattleChildViewModel() {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val escapeCommand = 0
    val attackCommand = 1

    override fun isBoundedImpl(commandType: BattleCommandType): Boolean {
        return commandType == EscapeCommand
    }

    override fun goNextImpl() {
        when (selectManager.selected) {
            attackCommand -> {
                commandRepository.pop()
            }

            escapeCommand -> {
                this.commandRepository.push(
                    FinishCommand(
                        battleResult = BattleResult.Escape,
                    )
                )
            }
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
