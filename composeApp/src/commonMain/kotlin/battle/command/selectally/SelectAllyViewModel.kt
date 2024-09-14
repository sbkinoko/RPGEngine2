package battle.command.selectally

import battle.BattleChildViewModel
import battle.domain.CommandType
import battle.domain.SelectAllyCommand
import battle.usecase.changeselectingactionplayer.ChangeSelectingActionPlayerUseCase
import common.values.playerNum
import menu.domain.SelectManager
import org.koin.core.component.inject

class SelectAllyViewModel : BattleChildViewModel() {
    private val changeSelectingActionPlayerUseCase: ChangeSelectingActionPlayerUseCase by inject()

    override val canBack: Boolean
        get() = true

    override fun isBoundedImpl(commandType: CommandType): Boolean {
        return commandType is SelectAllyCommand
    }

    override fun goNextImpl() {
        changeSelectingActionPlayerUseCase.invoke()
    }

    override var selectManager: SelectManager = SelectManager(
        width = playerNum,
        itemNum = playerNum,
    )
}
