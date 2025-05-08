package gamescreen.battle.command.escape

import core.repository.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.battle.BattleChildViewModel
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.EscapeCommand
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
                screenTypeRepository.setScreenType(
                    gameScreenType = GameScreenType.FIELD,
                )
            }
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
