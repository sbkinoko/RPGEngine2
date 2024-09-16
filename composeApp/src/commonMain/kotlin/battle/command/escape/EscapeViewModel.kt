package battle.command.escape

import battle.BattleChildViewModel
import battle.domain.BattleCommandType
import battle.domain.EscapeCommand
import main.domain.ScreenType
import main.repository.screentype.ScreenTypeRepository
import menu.domain.SelectManager
import org.koin.core.component.inject

class EscapeViewModel : BattleChildViewModel() {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    val escapeCommand = 0
    val attackCommand = 1

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(battleCommandType: BattleCommandType): Boolean {
        return battleCommandType == EscapeCommand
    }

    override fun goNextImpl() {
        when (selectManager.selected) {
            attackCommand -> {
                commandStateRepository.pop()
            }

            escapeCommand -> {
                screenTypeRepository.screenType = ScreenType.FIELD
            }
        }
    }

    override var selectManager: SelectManager = SelectManager(
        width = 2,
        itemNum = 2,
    )
}
